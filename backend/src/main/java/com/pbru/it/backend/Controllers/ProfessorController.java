package com.pbru.it.backend.Controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pbru.it.backend.Models.Professor;
import com.pbru.it.backend.Services.ProfessorService;


@RestController
@RequestMapping("/professor")
public class ProfessorController {
    
    @Autowired
    private ProfessorService professorService;

    @GetMapping
    public ResponseEntity<List<Professor>> findAll() {
        return ResponseEntity.ok(professorService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Professor> findById(@PathVariable UUID id) {
        return professorService.findById(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping("/register")
    public ResponseEntity<Professor> register(@RequestBody Professor professor) {
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
