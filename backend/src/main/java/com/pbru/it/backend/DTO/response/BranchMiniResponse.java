package com.pbru.it.backend.DTO.response;

import com.pbru.it.backend.DTO.BranchMajor;
import com.pbru.it.backend.Models.CourseLanguage;

public class BranchMiniResponse {

    private Long id;

    private BranchMajor major;

    private String nameTh;

    private String nameEn;

    private CourseLanguage language;

    private String image;

    private boolean status;

    public BranchMiniResponse() {
    }

    public BranchMiniResponse(Long id, BranchMajor major, String nameTh, String nameEn, CourseLanguage language,
            String image, boolean status) {
        this.id = id;
        this.major = major;
        this.nameTh = nameTh;
        this.nameEn = nameEn;
        this.language = language;
        this.image = image;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
