package com.pbru.it.backend.DTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProfessorProfileResponse {
    private Long id;
    private String nameTh;
    private String nameEn;
    private LocalDate birthday;
    private String major;
    private String image;
} 
