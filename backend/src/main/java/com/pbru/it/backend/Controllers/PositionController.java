package com.pbru.it.backend.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pbru.it.backend.Models.Position;
import com.pbru.it.backend.Services.PositionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/position")
public class PositionController {
    
    @Autowired
    private PositionService positionService;

    @GetMapping
    public ResponseEntity<List<Position>> findAll() {
        return ResponseEntity.ok(positionService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Position> findById(@PathVariable int id) {
        return positionService.findById(id).map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.noContent().build());
    }

    @PostMapping
    public ResponseEntity<Position> save(@RequestBody Position position) {
        return ResponseEntity.status(201).body(position);
    }
    
    @PostMapping("/all")
    public ResponseEntity<List<Position>> saveAll(@RequestBody List<Position> positions) {
        return ResponseEntity.status(201).body(positionService.saveAll(positions));
    }

}
