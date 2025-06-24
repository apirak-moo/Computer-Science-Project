package com.pbru.it.backend.DTO;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.pbru.it.backend.Models.BranchCourse;
import com.pbru.it.backend.Models.CourseLanguage;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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

    public BranchResponse(Long id, BranchMajor branchMajor, String nameTh, String nameEn, BigDecimal tuitionFees,
            CourseLanguage language, Set<BranchCourse> courses, String image, String description, String careers,
            boolean status) {
        this.id = id;
        this.major = branchMajor;
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

}
