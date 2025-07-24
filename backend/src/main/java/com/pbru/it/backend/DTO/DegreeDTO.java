package com.pbru.it.backend.DTO;

import java.util.Set;

public class DegreeDTO {
    private String nameTh;
    private String nameEn;
    private Set<ProgramDTO> programs;
    public DegreeDTO() {
    }
    public DegreeDTO(String nameTh, String nameEn, Set<ProgramDTO> programs) {
        this.nameTh = nameTh;
        this.nameEn = nameEn;
        this.programs = programs;
    }
    public String getNameTh() {
        return nameTh;
    }
    public void setNameTh(String nameTh) {
        this.nameTh = nameTh;
    }
    public String getNameEn() {
        return nameEn;
    }
    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }
    public Set<ProgramDTO> getPrograms() {
        return programs;
    }
    public void setPrograms(Set<ProgramDTO> programs) {
        this.programs = programs;
    }
}
