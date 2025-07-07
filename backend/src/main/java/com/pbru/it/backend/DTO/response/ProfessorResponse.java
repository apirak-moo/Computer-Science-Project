package com.pbru.it.backend.DTO.response;

import java.util.List;
import java.util.UUID;

import com.pbru.it.backend.Models.ProfessorEducationalQualification;


public record ProfessorResponse(
    UUID id,
    String email,
    List<PositionResponse> positions,
    ProfessorProfileResponse profile,
    List<ProfessorEducationalQualification> qualifications
) {
} 

