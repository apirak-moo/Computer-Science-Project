package com.pbru.it.backend.Controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pbru.it.backend.DTO.ProfessorResearchWorkRequest;
import com.pbru.it.backend.Models.ProfessorResearchWork;
import com.pbru.it.backend.Services.ProfessorResearchWorkService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/professor_research_work")
public class ProfessorResearchWorkController {

    @Autowired
    private ProfessorResearchWorkService professorResearchWorkService;

    @GetMapping
    public ResponseEntity<List<ProfessorResearchWork>> getAllWorks() {
        return ResponseEntity.ok(professorResearchWorkService.getAllWorks());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Optional<ProfessorResearchWork>> getWorkById(@PathVariable Long id) {
        return ResponseEntity.ok(professorResearchWorkService.getWork(id));
    }

    @PostMapping
    public ResponseEntity<ProfessorResearchWork> createWork(@ModelAttribute ProfessorResearchWorkRequest request) {
        ProfessorResearchWork work = professorResearchWorkService.createWork(request);
        URI location = URI.create("/professor_research_work/"+work.getId());
        return ResponseEntity.created(location).body(work);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ProfessorResearchWork> updateById(@PathVariable Long id, @ModelAttribute ProfessorResearchWorkRequest request) {
        return ResponseEntity.ok().body(professorResearchWorkService.updateById(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        professorResearchWorkService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
}
