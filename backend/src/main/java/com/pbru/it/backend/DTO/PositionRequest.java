package com.pbru.it.backend.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;

public record PositionRequest(
    int id,
    @NotBlank @Max(100) String nameTh,
    @NotBlank @Max(100) String nameEn
) {

}
