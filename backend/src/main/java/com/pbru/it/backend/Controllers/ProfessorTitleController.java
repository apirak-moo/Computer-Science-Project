package com.pbru.it.backend.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pbru.it.backend.Models.ProfessorTitle;
import com.pbru.it.backend.Services.ProfessorTitleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/professor_title")
public class ProfessorTitleController {

    @Autowired
    private ProfessorTitleService professorTitleService;

    @GetMapping
    public List<ProfessorTitle> getAllTitles() {
        return professorTitleService.getAllTitles();
    }

   @GetMapping("/{id}")
   public Optional<ProfessorTitle> getTitleById(@PathVariable Long id) {
       return professorTitleService.getTitleById(id);
   }

}
