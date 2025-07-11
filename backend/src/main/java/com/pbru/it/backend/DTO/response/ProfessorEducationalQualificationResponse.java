package com.pbru.it.backend.DTO.response;

import com.pbru.it.backend.Models.Degree;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProfessorEducationalQualificationResponse {
    private Long id;
    private String university;
    private String major;
    private Degree degree;
}
