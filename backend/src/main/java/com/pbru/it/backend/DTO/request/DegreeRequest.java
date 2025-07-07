package com.pbru.it.backend.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DegreeRequest {
    private int id;
    private String nameTh;
    private String nameEn;
}
