package com.pbru.it.backend.DTO.request;

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
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorProfileRequest {
    private Long id;

    @NotNull(message = "กรุณาเลือกตำแหน่งทางวิชาการ")
    private int titleId;

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank @Size(max = 12)
    private String phone;

    @NotBlank
    private String git;

    @NotBlank
    @Size(max = 100)
    private String major;

    private MultipartFile image;

}
