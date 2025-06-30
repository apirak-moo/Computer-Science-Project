package com.pbru.it.backend.DTO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProfessorRequest {

    private UUID id;

    @Email @Size(min = 1, max = 100) @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotNull
    private List<Integer> positions = new ArrayList<>();

    @NotNull
    private List<ProfessorEducationalQualificationRequest> qualifications = new ArrayList<>();

    @NotNull
    private ProfessorProfileRequest profile;

    private List<ProfessorResearchWorkRequest> works = new ArrayList<>();

}

