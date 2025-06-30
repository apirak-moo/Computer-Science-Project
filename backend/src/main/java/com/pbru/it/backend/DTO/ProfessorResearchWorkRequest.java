package com.pbru.it.backend.DTO;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProfessorResearchWorkRequest {
    @NotNull
    private Long id;
    @NotNull
    private UUID professorId;
    @NotBlank @Max(150)
    private String title;
    @NotBlank
    private String description;
    @NotNull
    private List<MultipartFile> images;
}
