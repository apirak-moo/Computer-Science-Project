package com.pbru.it.backend.Controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.pbru.it.backend.DTO.request.ProfessorRequest;
import com.pbru.it.backend.DTO.response.ProfessorResponse;
import com.pbru.it.backend.Services.ProfessorService;

@RestController
@RequestMapping("/professor")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @GetMapping
    public ResponseEntity<Page<ProfessorResponse>> findAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String major,
            @PageableDefault(size = 8, page = 0) Pageable pageable) {
        return ResponseEntity.ok(professorService.findAll(name, major, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(professorService.findById(id));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> editProfessor(
            @PathVariable UUID id,
            @RequestPart("request") ProfessorRequest request,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            
            if (request.getProfile() != null && image != null && !image.isEmpty()) {
                request.getProfile().setImage(image);
            }

            return ResponseEntity.ok(professorService.updateById(id, request));
        } catch (Exception ex) {
            ex.printStackTrace(); // ⬅ สำคัญมาก จะบอกว่า error มาจากอะไร
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        professorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
