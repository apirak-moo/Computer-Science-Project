package com.pbru.it.backend.DTO;

import java.math.BigDecimal;

import com.pbru.it.backend.Models.CourseLanguage;
import com.pbru.it.backend.Models.Program;

import lombok.Builder;

@Builder
public class BranchMajor {

    private Long id;

    private Program program;

    private String nameTh;

    private String nameEn;

    private BigDecimal tuitionFees;

    private CourseLanguage language;

    private String image;

    private String description;

    private String careers;

    private boolean status;

    public BranchMajor() {
    }

    public BranchMajor(Long id, Program program, String nameTh, String nameEn, BigDecimal tuitionFees,
            CourseLanguage language, String image, String description, String careers, boolean status) {
        this.id = id;
        this.program = program;
        this.nameTh = nameTh;
        this.nameEn = nameEn;
        this.tuitionFees = tuitionFees;
        this.language = language;
        this.image = image;
        this.description = description;
        this.careers = careers;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
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

    public BigDecimal getTuitionFees() {
        return tuitionFees;
    }

    public void setTuitionFees(BigDecimal tuitionFees) {
        this.tuitionFees = tuitionFees;
    }

    public CourseLanguage getLanguage() {
        return language;
    }

    public void setLanguage(CourseLanguage language) {
        this.language = language;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCareers() {
        return careers;
    }

    public void setCareers(String careers) {
        this.careers = careers;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
}
