package com.pbru.it.backend.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pbru.it.backend.Models.ProfessorTitle;
import com.pbru.it.backend.Services.ProfessorTitleService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/professor_title")
public class ProfessorTitleController {

    private final ProfessorTitleService professorTitleService;

    public ProfessorTitleController(ProfessorTitleService professorTitleService) {
        this.professorTitleService = professorTitleService;
    }

    @GetMapping
    public List<ProfessorTitle> getAllTitles() {
        return professorTitleService.getAllTitles();
    }

    @GetMapping("/{id}")
    public Optional<ProfessorTitle> getTitleById(@PathVariable Long id) {
        return professorTitleService.getTitleById(id);
    }

}
