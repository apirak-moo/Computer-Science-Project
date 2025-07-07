package com.pbru.it.backend.Services;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.pbru.it.backend.DTO.request.PositionRequest;
import com.pbru.it.backend.DTO.request.ProfessorRequest;
import com.pbru.it.backend.DTO.response.ProfessorResponse;
import com.pbru.it.backend.Mapper.ProfessorMapper;
import com.pbru.it.backend.Models.Degree;
import com.pbru.it.backend.Models.Position;
import com.pbru.it.backend.Models.Professor;
import com.pbru.it.backend.Models.ProfessorEducationalQualification;
import com.pbru.it.backend.Models.ProfessorProfile;
import com.pbru.it.backend.Models.ProfessorTitle;
import com.pbru.it.backend.Models.QProfessor;
import com.pbru.it.backend.Repositories.DegreeRepository;
import com.pbru.it.backend.Repositories.ProfessorRepository;
import com.pbru.it.backend.Repositories.ProfessorTitleRepository;

@Service
public class ProfessorService {

    private final ProfessorRepository professorRepository;

    private final ProfessorTitleRepository professorTitleRepository;

    private final DegreeRepository degreeRepository;

    private final ProfessorMapper mapper;

    private final FileService fileService;

    private final PasswordEncoder encoder;

    public ProfessorService(ProfessorRepository professorRepository, ProfessorTitleRepository professorTitleRepository,
            DegreeRepository degreeRepository, ProfessorMapper mapper, FileService fileService,
            PasswordEncoder encoder) {
        this.professorRepository = professorRepository;
        this.professorTitleRepository = professorTitleRepository;
        this.degreeRepository = degreeRepository;
        this.mapper = mapper;
        this.fileService = fileService;
        this.encoder = encoder;
    }

    public Page<ProfessorResponse> findAll(String name, String major, Pageable pageable) {
        QProfessor qProfessor = QProfessor.professor;
        BooleanBuilder builder = new BooleanBuilder();
        if (name != null && !name.isEmpty()) {
            builder.and(qProfessor.profile.name.containsIgnoreCase(name));
        }
        if (major != null && !major.isEmpty()) {
            builder.and(qProfessor.profile.major.containsIgnoreCase(major));
        }
        Predicate predicate = builder.getValue();
        Page<Professor> page = (predicate != null)
                ? professorRepository.findAll(predicate, pageable)
                : professorRepository.findAll(pageable); // ✅ fallback ไม่ใช้ predicate
        List<ProfessorResponse> content = mapper.toProfessorResponseList(page.getContent());
        return new PageImpl<>(content, pageable, page.getTotalElements());
    }

    public ProfessorResponse findById(UUID id) {
        return mapper.toProfessorResponse(professorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professor not found with id: " + id)));
    }

    public ProfessorResponse createProfessor(ProfessorRequest professorRequest) {
        Professor professor = new Professor();
        professor.setEmail(professorRequest.getEmail());
        professor.setPassword(encoder.encode(professorRequest.getPassword()));
        Set<Position> positions = professor.getPositions();
        for (PositionRequest position : professorRequest.getPositions()) {
            Position newPosition = new Position();
            newPosition.setId(position.id());
            newPosition.setName(position.name());
            positions.add(newPosition);
        }
        professor.setPositions(positions);
        ProfessorProfile profile = new ProfessorProfile();
        profile.setName(professorRequest.getProfile().getName());
        profile.setMajor(professorRequest.getProfile().getMajor());
        profile.setPhone(professorRequest.getProfile().getPhone());
        profile.setGit(professorRequest.getProfile().getGit());
        ProfessorTitle title = professorTitleRepository.findById(professorRequest.getProfile().getTitleId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Invalid titleId: " + professorRequest.getProfile().getTitleId()));
        profile.setTitle(title);
        profile.setProfessor(professor);

        MultipartFile image = professorRequest.getProfile().getImage();
        if (image != null && !image.isEmpty()) {
            profile.setImage(fileService.uploadImageFile(image));
        } else {
            throw new RuntimeException("ต้องมีรูปภาพโปรไฟล์");
        }

        professor.setProfile(profile);
        List<ProfessorEducationalQualification> qualifications = professor.getQualifications();
        for (var qualification : professorRequest.getQualifications()) {
            ProfessorEducationalQualification newQualification = new ProfessorEducationalQualification();
            newQualification.setUniversity(qualification.getUniversity());
            newQualification.setMajor(qualification.getMajor());
            Degree degree = degreeRepository.findById(qualification.getDegreeId()).orElseThrow(
                    () -> new IllegalArgumentException("Invalid degreeId: " + qualification.getDegreeId()));
            newQualification.setDegree(degree);
            newQualification.setProfessor(professor);
            qualifications.add(newQualification);
        }
        professor.setQualifications(qualifications);
        professorRepository.save(professor);
        return mapper.toProfessorResponse(professor);
    }

    public ProfessorResponse updateById(UUID id, ProfessorRequest professorRequest) {
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professor not found with id: " + id));
        professor.setEmail(professorRequest.getEmail());
        // professor.setPassword(professorRequest.getPassword());
        Set<Position> positions = professor.getPositions();
        if (!professorRequest.getPositions().isEmpty() && professorRequest.getPositions() != null) {
            professor.getPositions().clear();
            for (PositionRequest position : professorRequest.getPositions()) {
                Position newPosition = new Position();
                newPosition.setId(position.id());
                newPosition.setName(position.name());
                positions.add(newPosition);
            }
        }
        professor.setPositions(positions);
        ProfessorProfile profile = professor.getProfile();
        if (professorRequest.getProfile() != null) {
            profile.setName(professorRequest.getProfile().getName());
            profile.setMajor(professorRequest.getProfile().getMajor());
            profile.setPhone(professorRequest.getProfile().getPhone());
            profile.setGit(professorRequest.getProfile().getGit());
            ProfessorTitle title = professorTitleRepository.findById(professorRequest.getProfile().getTitleId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Invalid titleId: " + professorRequest.getProfile().getTitleId()));
            profile.setTitle(title);
            profile.setProfessor(professor);
            MultipartFile image = professorRequest.getProfile().getImage();
            if (image != null && !image.isEmpty()) {
                profile.setImage(fileService.uploadImageFile(image));
            }
        }
        professor.setProfile(profile);
        List<ProfessorEducationalQualification> qualifications = professor.getQualifications();
        if (!professorRequest.getQualifications().isEmpty() && professorRequest.getQualifications() != null) {
            professor.getQualifications().clear();
            for (var qualification : professorRequest.getQualifications()) {
                ProfessorEducationalQualification newQualification = new ProfessorEducationalQualification();
                newQualification.setUniversity(qualification.getUniversity());
                newQualification.setMajor(qualification.getMajor());
                Degree degree = degreeRepository.findById(qualification.getDegreeId()).orElseThrow(
                        () -> new IllegalArgumentException("Invalid degreeId: " + qualification.getDegreeId()));
                newQualification.setDegree(degree);
                newQualification.setProfessor(professor);
                qualifications.add(newQualification);
            }
        }
        professor.setQualifications(qualifications);
        professorRepository.save(professor);
        return mapper.toProfessorResponse(professor);
    }

    public void deleteById(UUID id) {
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professor not found with id: " + id));
        fileService.deleteImageFile(professor.getProfile().getMajor());
        professorRepository.delete(professor);
    }

}
