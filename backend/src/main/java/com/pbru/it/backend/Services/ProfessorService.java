package com.pbru.it.backend.Services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pbru.it.backend.DTO.ProfessorResponse;
import com.pbru.it.backend.Mapper.ProfessorMapper;
import com.pbru.it.backend.DTO.ProfessorEducationalQualificationRequest;
import com.pbru.it.backend.DTO.ProfessorExpertiseRequest;
import com.pbru.it.backend.DTO.ProfessorRequest;
import com.pbru.it.backend.Models.Degree;
import com.pbru.it.backend.Models.Position;
import com.pbru.it.backend.Models.Professor;
import com.pbru.it.backend.Models.ProfessorEducationalQualification;
import com.pbru.it.backend.Models.ProfessorExpertise;
import com.pbru.it.backend.Models.ProfessorProfile;
import com.pbru.it.backend.Models.ProfessorTitle;
import com.pbru.it.backend.Models.QProfessor;
import com.pbru.it.backend.Models.QProfessorEducationalQualification;
import com.pbru.it.backend.Models.QProfessorProfile;
import com.pbru.it.backend.Models.QProfessorTitle;
import com.pbru.it.backend.Repositories.DegreeRepository;
import com.pbru.it.backend.Repositories.PositionRepository;
import com.pbru.it.backend.Repositories.ProfessorRepository;
import com.pbru.it.backend.Repositories.ProfessorTitleRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Service
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final FileService fileService;
    private final PositionRepository positionRepository;
    private final DegreeRepository degreeRepository;
    private final JPAQueryFactory queryFactory;
    private final ProfessorMapper professorMapper;
    private final ProfessorTitleRepository professorTitleRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public ProfessorService(
            ProfessorRepository professorRepository,
            FileService fileService,
            PositionRepository positionRepository,
            DegreeRepository degreeRepository,
            JPAQueryFactory queryFactory,
            ProfessorMapper professorMapper,
            ProfessorTitleRepository professorTitleRepository) {
        this.professorRepository = professorRepository;
        this.fileService = fileService;
        this.positionRepository = positionRepository;
        this.degreeRepository = degreeRepository;
        this.queryFactory = queryFactory;
        this.professorMapper = professorMapper;
        this.professorTitleRepository = professorTitleRepository;
    }

    public Page<ProfessorResponse> findAll(String nameTh, String major, Pageable pageable) {

        QProfessor professor = QProfessor.professor;
        QProfessorProfile profile = QProfessorProfile.professorProfile;
        QProfessorTitle title = QProfessorTitle.professorTitle;
        QProfessorEducationalQualification qualification = QProfessorEducationalQualification.professorEducationalQualification;

        BooleanExpression predicate = null;

        if (nameTh != null && !nameTh.isEmpty()) {
            predicate = profile.nameTh.startsWith(nameTh);
        }

        if (major != null && !major.isEmpty()) {
            BooleanExpression majorPredicate = profile.major.eq(major);
            predicate = predicate == null ? majorPredicate : predicate.and(majorPredicate);
        }

        List<Professor> content = queryFactory
                .select(professor)
                .from(professor)
                .leftJoin(professor.profile, profile).fetchJoin()
                .leftJoin(professor.profile.title, title).fetchJoin()
                .leftJoin(professor.qualifications, qualification).fetchJoin()
                .where(predicate)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(
                        professor.id.asc(),
                        professor.profile.nameTh.asc(),
                        professor.profile.birthday.asc(),
                        qualification.degree.id.asc(),
                        qualification.id.asc()) // ✅
                                                // ตรงนี้สำคัญ
                .fetch(); // ✅ ตรงนี้คือ fetch actual result

        long total = queryFactory
                .select(professor.count())
                .from(professor)
                .leftJoin(professor.profile, profile)
                .where(predicate)
                .fetchOne();

        return new PageImpl<>(professorMapper.toProfessorResponseList(content), pageable, total);
    }

    public Optional<Professor> findById(UUID id) {
        return professorRepository.findById(id);
    }

    public Professor save(ProfessorRequest request) {

        Professor professor = new Professor();

        professor.setEmail(request.getEmail());
        professor.setPassword(encoder.encode(request.getPassword()));

        if (request.getPositions().isEmpty()) {
            throw new IllegalArgumentException("ต้องเลือกอย่างน้อย 1 ตำแหน่ง");
        }

        Set<Position> positions = new HashSet<>();
        for (int item : request.getPositions()) {
            Position position = positionRepository.findById(item)
                    .orElseThrow(() -> new RuntimeException("ไม่พบ Position Id : " + item));
            positions.add(position);
        }

        List<ProfessorEducationalQualification> qualifications = new ArrayList<>();
        for (ProfessorEducationalQualificationRequest item : request.getQualifications()) {
            ProfessorEducationalQualification qualification = new ProfessorEducationalQualification();
            Degree degree = degreeRepository.findById(item.getDegreeId())
                    .orElseThrow(() -> new RuntimeException("ไม่พบ Degree Id : " + item.getDegreeId()));
            qualification.setDegree(degree);
            qualification.setUniversity(item.getUniversity());
            qualification.setMajor(item.getMajor());
            qualification.setYearGraduation(item.getYearGraduation());
            qualification.setProfessor(professor);
            qualifications.add(qualification);
        }
        professor.setQualifications(qualifications);
        professor.setPositions(positions);

        ProfessorProfile profile = new ProfessorProfile();

        ProfessorTitle title = professorTitleRepository.findById(request.getProfile().getTitleId())
                .orElseThrow(() -> new RuntimeException("ไม่พบ Title Id : " + request.getProfile().getTitleId()));
        profile.setTitle(title);

        profile.setNameTh(request.getProfile().getNameTh());
        profile.setNameEn(request.getProfile().getNameEn());
        profile.setBirthday(request.getProfile().getBirthday());
        profile.setPhone(request.getProfile().getPhone());
        profile.setGit(request.getProfile().getGit());
        profile.setMajor(request.getProfile().getMajor());
        profile.setImage(fileService.uploadImageFile(request.getProfile().getImage()));
        profile.setProfessor(professor);

        List<ProfessorExpertise> expertises = new ArrayList<ProfessorExpertise>();
        for (ProfessorExpertiseRequest ex : request.getProfile().getExpertises()) {
            ProfessorExpertise professorExpertise = new ProfessorExpertise();
            professorExpertise.setName(ex.name());
            professorExpertise.setProfile(profile);
            expertises.add(professorExpertise);
        }

        profile.setExpertises(expertises);

        professor.setProfile(profile);

        return professorRepository.save(professor);

    }

    public Professor updateById(UUID id, ProfessorRequest request) {
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ไม่พบ Professor Id : " + request.getId()));
        professor.setEmail(request.getEmail());
        professor.setPassword(encoder.encode(request.getPassword()));

        if (request.getPositions().isEmpty()) {
            throw new IllegalArgumentException("ต้องเลือกอย่างน้อย 1 ตำแหน่ง");
        }

        Set<Position> positions = new HashSet<>();
        for (int item : request.getPositions()) {
            Position position = positionRepository.findById(item)
                    .orElseThrow(() -> new RuntimeException("ไม่พบ Position Id : " + item));
            positions.add(position);
        }
        professor.getPositions().clear();
        professor.getPositions().addAll(positions);

        ProfessorProfile profile = professor.getProfile();
        if (profile == null) {
            profile = new ProfessorProfile();
            profile.setProfessor(professor); // เชื่อมความสัมพันธ์ใหม่
        }

        ProfessorTitle title = professor.getProfile().getTitle();
        if (request.getProfile().getTitleId() == null) {
            title = professorTitleRepository.findById(request.getProfile().getId())
                    .orElseThrow(() -> new RuntimeException("ไม่พบ Title Id : " + request.getProfile().getTitleId()));
        }

        profile.setTitle(title);
        profile.setNameTh(request.getProfile().getNameTh());
        profile.setNameEn(request.getProfile().getNameEn());
        profile.setBirthday(request.getProfile().getBirthday());
        profile.setPhone(request.getProfile().getPhone());
        profile.setGit(request.getProfile().getGit());
        profile.setMajor(request.getProfile().getMajor());
        profile.setImage(fileService.uploadImageFile(request.getProfile().getImage()));
        profile.setProfessor(professor);

        List<ProfessorExpertise> expertises = profile.getExpertises();
        expertises.clear();
        for (ProfessorExpertiseRequest ex : request.getProfile().getExpertises()) {
            ProfessorExpertise professorExpertise = new ProfessorExpertise();
            professorExpertise.setName(ex.name());
            professorExpertise.setProfile(profile);
            expertises.add(professorExpertise);
        }

        profile.getExpertises().addAll(expertises);

        professor.setProfile(profile);

        professor.getQualifications().clear();
        Set<ProfessorEducationalQualification> qualifications = new HashSet<>();
        for (ProfessorEducationalQualificationRequest item : request.getQualifications()) {
            ProfessorEducationalQualification qualification = new ProfessorEducationalQualification();
            Degree degree = degreeRepository.findById(item.getDegreeId())
                    .orElseThrow(() -> new RuntimeException("ไม่พบ Degree Id : " + item.getDegreeId()));
            qualification.setDegree(degree);
            qualification.setUniversity(item.getUniversity());
            qualification.setMajor(item.getMajor());
            qualification.setYearGraduation(item.getYearGraduation());
            qualification.setProfessor(professor);
            qualifications.add(qualification);
        }
        professor.getQualifications().addAll(qualifications);

        return professorRepository.save(professor);
    }

    public void deleteById(UUID id) {
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ไม่พบผู้ใช้งาน ID: " + id));
        fileService.deleteImageFile(professor.getProfile().getImage());
        professorRepository.deleteById(id);
    }

}
