package com.pbru.it.backend.DTO.response;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.pbru.it.backend.DTO.BranchMajor;
import com.pbru.it.backend.Models.BranchCourse;
import com.pbru.it.backend.Models.CourseLanguage;

public class BranchResponse {

    private Long id;

    private BranchMajor major;

    private String nameTh;

    private String nameEn;

    private BigDecimal tuitionFees;

    private CourseLanguage language;

    private Set<BranchCourse> courses = new HashSet<>();

    private String image;

    private String description;

    private String careers;

    private boolean status;

    public BranchResponse() {
    }

    public BranchResponse(Long id, BranchMajor major, String nameTh, String nameEn, BigDecimal tuitionFees,
            CourseLanguage language, Set<BranchCourse> courses, String image, String description, String careers,
            boolean status) {
        this.id = id;
        this.major = major;
        this.nameTh = nameTh;
        this.nameEn = nameEn;
        this.tuitionFees = tuitionFees;
        this.language = language;
        this.courses = courses;
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

    public BranchMajor getMajor() {
        return major;
    }

    public void setMajor(BranchMajor major) {
        this.major = major;
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

    public Set<BranchCourse> getCourses() {
        return courses;
    }

    public void setCourses(Set<BranchCourse> courses) {
        this.courses = courses;
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
