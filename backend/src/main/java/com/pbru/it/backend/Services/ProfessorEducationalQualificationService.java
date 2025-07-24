package com.pbru.it.backend.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pbru.it.backend.DTO.response.ProfessorEducationalQualificationResponse;
import com.pbru.it.backend.Mapper.ProfessorMapper;
import com.pbru.it.backend.Repositories.ProfessorEducationalQualificationRepository;

@Service
public class ProfessorEducationalQualificationService {
    
    private final ProfessorEducationalQualificationRepository professorEducationalQualificationRepository;
    private final ProfessorMapper professorMapper;

    public ProfessorEducationalQualificationService(
            ProfessorEducationalQualificationRepository professorEducationalQualificationRepository,
            ProfessorMapper professorMapper) {
        this.professorEducationalQualificationRepository = professorEducationalQualificationRepository;
        this.professorMapper = professorMapper;
    }

    List<ProfessorEducationalQualificationResponse> getAllProfessorEducationalQualifications() {
        return professorMapper.toProfessorEducationalQualificationResponseList(professorEducationalQualificationRepository.findAll());
    }

}
