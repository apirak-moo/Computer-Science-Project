package com.pbru.it.backend.DTO;

import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProfessorRequest(
        UUID id,
        @Email @Size(min = 1, max = 100) @NotBlank String email,
        @NotBlank String password,
        @NotNull ProfessorRole role,
        @NotNull @Valid ProfessorProfileRequest profile) {
}
