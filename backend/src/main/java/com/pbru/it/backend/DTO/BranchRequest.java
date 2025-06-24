package com.pbru.it.backend.DTO;

import com.pbru.it.backend.Models.CourseLanguage;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchRequest {

    private Long majorId;

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
