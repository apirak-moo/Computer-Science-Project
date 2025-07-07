package com.pbru.it.backend.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.pbru.it.backend.Models.ProfessorTitle;
import com.pbru.it.backend.Repositories.ProfessorTitleRepository;

@Service
public class ProfessorTitleService {

    private final ProfessorTitleRepository professorTitleRepository;

    public ProfessorTitleService(ProfessorTitleRepository professorTitleRepository) {
        this.professorTitleRepository = professorTitleRepository;
    }

    public List<ProfessorTitle> getAllTitles() {
        return professorTitleRepository.findAll();
    }

    public Optional<ProfessorTitle> getTitleById(Long id) {
        return professorTitleRepository.findById(id.intValue());
    }

}
