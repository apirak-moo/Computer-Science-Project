package com.pbru.it.backend.Services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pbru.it.backend.DTO.ProfessorResponse;
import com.pbru.it.backend.DTO.ProfessorProfileResponse;
import com.pbru.it.backend.DTO.ProfessorRequest;
import com.pbru.it.backend.Models.Professor;
import com.pbru.it.backend.Models.ProfessorProfile;
import com.pbru.it.backend.Models.QProfessor;
import com.pbru.it.backend.Models.QProfessorProfile;
import com.pbru.it.backend.Repositories.ProfessorRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private FileService fileService;

    private final JPAQueryFactory queryFactory;

    public ProfessorService(JPAQueryFactory jpaQueryFactory) {
        this.queryFactory = jpaQueryFactory;
    }

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public Page<ProfessorResponse> findAll(String nameTh, String major, Pageable pageable) {

        QProfessor professor = QProfessor.professor;
        QProfessorProfile profile = QProfessorProfile.professorProfile;

        BooleanExpression predicate = null;

        if (nameTh != null && !nameTh.isEmpty()) {
            predicate = profile.nameTh.startsWith(nameTh);
        }

        if (major != null && !major.isEmpty()) {
            BooleanExpression majorPredicate = profile.major.eq(major);
            predicate = predicate == null ? majorPredicate : predicate.and(majorPredicate);
        }

        List<ProfessorResponse> content = queryFactory
                .select(Projections.constructor(
                        ProfessorResponse.class,
                        professor.id,
                        professor.email,
                        professor.password,
                        professor.role,
                        Projections.constructor(
                                ProfessorProfileResponse.class,
                                profile.id,
                                profile.nameTh,
                                profile.nameEn,
                                profile.birthday,
                                profile.major,
                                profile.image)))
                .from(professor)
                .leftJoin(professor.profile, profile)
                .where(predicate)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(professor.id.asc(), professor.profile.nameTh.asc(), professor.profile.birthday.asc()) // ✅
                                                                                                               // ตรงนี้สำคัญ
                .fetch(); // ✅ ตรงนี้คือ fetch actual result

        long total = queryFactory
                .select(professor.count())
                .from(professor)
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    public Optional<Professor> findById(UUID id) {
        return professorRepository.findById(id);
    }

    public Professor save(ProfessorRequest request) {

        Professor professor = new Professor();

        professor.setEmail(request.email());
        professor.setPassword(encoder.encode(request.password()));
        professor.setRole(request.role());

        ProfessorProfile profile = new ProfessorProfile();

        profile.setNameTh(request.profile().nameTh());
        profile.setNameEn(request.profile().nameEn());
        profile.setBirthday(request.profile().birthday());
        profile.setMajor(request.profile().major());
        profile.setImage(fileService.uploadImageFile(request.profile().image()));
        profile.setProfessor(professor);

        professor.setProfile(profile);

        return professorRepository.save(professor);

    }

    public Professor updateById(UUID id, Professor professor) {
        if (professorRepository.existsById(id)) {
            professor.setId(id);
            return professorRepository.save(professor);
        }
        return null;
    }

    public void deleteById(UUID id) {
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ไม่พบผู้ใช้งาน ID: " + id));
        fileService.deleteImageFile(professor.getProfile().getImage());
        professorRepository.deleteById(id);
    }

    // public String verify(Professor professor) {
    // Authentication authentication = authenticationManager.authenticate(
    // new UsernamePasswordAuthenticationToken(professor.getEmail(),
    // professor.getPassword()));
    // if (authentication.isAuthenticated()) {
    // return jwtService.generateToken(professor.getEmail());
    // }
    // return "Fail";
    // }

}
