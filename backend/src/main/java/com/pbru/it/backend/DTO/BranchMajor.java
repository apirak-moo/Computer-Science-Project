package com.pbru.it.backend.DTO;

import java.math.BigDecimal;

import com.pbru.it.backend.Models.CourseLanguage;
import com.pbru.it.backend.Models.Program;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
    
}
