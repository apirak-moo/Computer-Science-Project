package com.pbru.it.backend.DTO;

import java.time.LocalDate;
import java.util.List;

import com.pbru.it.backend.Models.ProfessorTitleResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProfessorProfileResponse {
    private Long id;
    private ProfessorTitleResponse title;
    private String nameTh;
    private String nameEn;
    private LocalDate birthday;
    private String phone;
    private String git;
    private String major;
    private String image;
    private List<ProfessorExpertiseResponse> expertises;
} 
