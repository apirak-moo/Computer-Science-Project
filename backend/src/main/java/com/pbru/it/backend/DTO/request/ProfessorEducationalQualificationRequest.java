package com.pbru.it.backend.DTO.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProfessorEducationalQualificationRequest {
    @NotBlank @Max(150)
    private String university;
    @NotBlank @Max(150)
    private String major;
    @NotNull
    private int degreeId;
}
