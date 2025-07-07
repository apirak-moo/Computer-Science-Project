package com.pbru.it.backend.DTO.request;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

import com.pbru.it.backend.Models.CourseLanguage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MajorRequest {

    private Long programId;

    private String nameTh;

    private String nameEn;

    private BigDecimal tuitionFees;

    private CourseLanguage language;

    private MultipartFile courseFile;

    private MultipartFile image;

    private String description;

    private String careers;

    private Boolean status;

}
