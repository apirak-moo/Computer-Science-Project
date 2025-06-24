package com.pbru.it.backend.Controllers;

import com.pbru.it.backend.Models.Program;
import com.pbru.it.backend.Services.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/program")
public class ProgramController {

    @Autowired
    private ProgramService programService;

    @GetMapping
    public ResponseEntity<List<Program>> findAll() {
        return ResponseEntity.ok(programService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Program>> findById(@PathVariable Long id) {
        Optional<Program> program = programService.findById(id);
        if(program.isPresent()) return ResponseEntity.ok(program);
        return ResponseEntity.notFound().build();
    }

}
