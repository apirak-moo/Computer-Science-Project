package com.pbru.it.backend.DTO.request;

import com.pbru.it.backend.Models.Major;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MajorCourseRequest {

    private Major major;

    private String code;

    private int year;

    private int sector;

    private String nameTh;

    private String nameEn;

}
