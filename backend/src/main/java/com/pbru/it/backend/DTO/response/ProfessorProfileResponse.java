package com.pbru.it.backend.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProfessorProfileResponse {
    private Long id;
    private ProfessorTitleResponse title;
    private String name;
    private String phone;
    private String git;
    private String major;
    private String image;
} 
