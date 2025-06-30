package com.pbru.it.backend.DTO;

import java.util.List;
import java.util.UUID;

import com.pbru.it.backend.Models.ProfessorEducationalQualification;
import com.pbru.it.backend.Models.ProfessorResearchWorkResponse;

public record ProfessorResponse(
    UUID id,
    String email,
    List<PositionResponse> positions,
    ProfessorProfileResponse profile,
    List<ProfessorEducationalQualification> qualifications,
    List<ProfessorResearchWorkResponse> works
) {
} 

