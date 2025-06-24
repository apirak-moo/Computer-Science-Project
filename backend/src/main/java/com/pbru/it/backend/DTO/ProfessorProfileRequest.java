package com.pbru.it.backend.DTO;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProfessorProfileRequest(
        Long id,
        @NotBlank String nameTh,
        @NotBlank String nameEn,
        @NotNull LocalDate birthday,
        @NotBlank @Size(min = 1, max = 100) String major,
        MultipartFile image) {
}
