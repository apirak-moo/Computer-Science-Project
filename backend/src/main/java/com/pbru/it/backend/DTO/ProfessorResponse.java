package com.pbru.it.backend.DTO;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProfessorResponse {
    private UUID id;
    private String email;
    private String password;
    private ProfessorRole role;
    private ProfessorProfileResponse profile;
}

