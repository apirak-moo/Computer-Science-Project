package com.pbru.it.backend.DTO;

import java.util.Set;

import lombok.Data;

@Data
public class DegreeDTO {
    private String nameTh;
    private String nameEn;
    private Set<ProgramDTO> programs;
}
