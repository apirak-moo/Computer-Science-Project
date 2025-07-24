package com.pbru.it.backend.DTO;

public class ProgramDTO {
    private String nameTh;
    private String nameEn;
    public ProgramDTO() {
    }
    public ProgramDTO(String nameTh, String nameEn) {
        this.nameTh = nameTh;
        this.nameEn = nameEn;
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
}
