package com.pbru.it.backend.DTO;

import com.pbru.it.backend.Models.CourseLanguage;

import lombok.Data;

@Data
public class BranchMiniResponse {

    private Long id;

    private BranchMajor major;

    private String nameTh;

    private String nameEn;

    private CourseLanguage language;

    private String image;

    private boolean status;

}
