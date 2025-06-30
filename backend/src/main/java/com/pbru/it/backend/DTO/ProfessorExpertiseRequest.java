package com.pbru.it.backend.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProfessorExpertiseRequest(
    Long id,
    @NotBlank @Size(max = 100)
    String name
) {

}
