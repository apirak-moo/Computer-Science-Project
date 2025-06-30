package com.pbru.it.backend.Models;

import java.time.LocalDateTime;
import java.util.List;

import com.pbru.it.backend.DTO.ProfessorResearchWorkImageResponse;

public record ProfessorResearchWorkResponse(
    Long id,
    String title,
    String description,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    List<ProfessorResearchWorkImageResponse> images
) {

}
