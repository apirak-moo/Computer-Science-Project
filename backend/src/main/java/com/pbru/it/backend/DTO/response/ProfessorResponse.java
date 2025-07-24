package com.pbru.it.backend.DTO.response;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record ProfessorResponse(
    UUID id,
    String email,
    @JsonIgnore
    String password,
    List<PositionResponse> positions,
    ProfessorProfileResponse profile,
    List<ProfessorEducationalQualificationResponse> qualifications
) {
} 

