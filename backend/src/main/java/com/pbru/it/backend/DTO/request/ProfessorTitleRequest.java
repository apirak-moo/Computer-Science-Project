package com.pbru.it.backend.DTO.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProfessorTitleRequest {
    private String id;
    @NotBlank @Size(max = 100)
    private String nameTh;
    @NotBlank @Size(max = 100)
    private String nameEn;
}
