package com.pbru.it.backend.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pbru.it.backend.DTO.DegreeDTO;
import com.pbru.it.backend.Models.Degree;
import com.pbru.it.backend.Services.DegreeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/degree")
public class DegreeController {
    
    @Autowired
    private DegreeService degreeService;

    @GetMapping
    public ResponseEntity<List<Degree>> findAll() {
        return ResponseEntity.ok(degreeService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Degree> findById(@PathVariable int id) {
        return degreeService.findById(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Degree> save(@RequestBody DegreeDTO degree) {
        return ResponseEntity.status(201).body(degreeService.save(degree));
    }
    
    // @PostMapping("/all")
    // public ResponseEntity<List<Degree>> save(@RequestBody List<DegreeDTO> request) {
    //     if(request.isEmpty()) return ResponseEntity.badRequest().build();
    //     Degree degree = degreeMapper.mapToDegree(request);
    //     Degree save = degreeService.save(null);
    //     return ResponseEntity.status(201).body(degreeService.saveAll(request));
    // }

}
