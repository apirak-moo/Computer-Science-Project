package com.pbru.it.backend.Controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pbru.it.backend.DTO.ProfessorRequest;
import com.pbru.it.backend.DTO.ProfessorResponse;
import com.pbru.it.backend.Models.Professor;
import com.pbru.it.backend.Services.ProfessorService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/professor")
public class ProfessorController {
    
    @Autowired
    private ProfessorService professorService;

    @GetMapping
    public ResponseEntity<Page<ProfessorResponse>> findAll(
        @RequestParam(required = false) String nameTh,
        @RequestParam(required = false) String major,
        @PageableDefault(size = 8) Pageable pageable
    ) {
        return ResponseEntity.ok(professorService.findAll(nameTh, major, pageable));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Professor> findById(@PathVariable UUID id) {
        return professorService.findById(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping("/register")
    public ResponseEntity<Professor> register(@Valid @ModelAttribute ProfessorRequest professor) {
        return ResponseEntity.status(201).body(professorService.save(professor));
    }

    // @PostMapping("/login")
    // public String login(@RequestBody Professor professor) {
    //     System.out.println(professor);
    //     return professorService.verify(professor);
    // }

    @PutMapping
    public ResponseEntity<Professor> editProfessor(@PathVariable UUID id, @RequestBody Professor professor){
        return ResponseEntity.ok(professorService.updateById(id, professor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        professorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
