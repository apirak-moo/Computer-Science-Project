package com.pbru.it.backend.DTO;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // ✅ Spring ต้องการ constructor ว่าง
@AllArgsConstructor
public class ProfessorProfileRequest {
    private Long id;

    @NotNull
    private Long titleId;

    @NotBlank
    @Size(max = 100)
    private String nameTh;

    @NotBlank
    @Size(max = 100)
    private String nameEn;

    @NotNull
    private LocalDate birthday;

    @NotBlank @Size(max = 12)
    private String phone;

    @NotBlank
    private String git;

    @NotBlank
    @Size(max = 100)
    private String major;

    private MultipartFile image;

    @NotNull
    private List<ProfessorExpertiseRequest> expertises;

}
