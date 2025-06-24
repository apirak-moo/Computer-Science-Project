package com.pbru.it.backend.Controllers;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pbru.it.backend.DTO.MajorRequest;
import com.pbru.it.backend.Models.Major;
import com.pbru.it.backend.Services.MajorService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/major")
public class MajorController {

    @Autowired
    private MajorService majorService;

    @GetMapping
    public ResponseEntity<Page<Major>> findAll(
            @RequestParam(required = false) String nameTh,
            @RequestParam(required = false) String nameEn,
            @RequestParam(required = false) Integer degreeId,
            @RequestParam(required = false) Boolean status,
            @PageableDefault(size = 8, sort = { "status" ,"id" , "program.id" , "nameEn" }, direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(majorService.filter(nameTh,nameEn,degreeId,status,pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Major>> findById(@PathVariable Long id) {
        Optional<Major> major = majorService.findById(id);
        if (major.isPresent())
            return ResponseEntity.ok(major);
        return ResponseEntity.notFound().build();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Major> create(@ModelAttribute MajorRequest majorRequest) {
        Major major = majorService.save(majorRequest);
        URI location = URI.create("/major/" + major.getId());
        return ResponseEntity.created(location).body(major);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Major> updateById(@PathVariable Long id, @ModelAttribute MajorRequest majorRequest) {
        return ResponseEntity.ok(majorService.updateById(id, majorRequest));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Major> patchStatusMajor(@PathVariable Long id, @RequestParam boolean status) {
        return ResponseEntity.ok(majorService.setStatusMajor(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        majorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
