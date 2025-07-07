package com.pbru.it.backend.DTO.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;

public record PositionRequest(
    int id,
    @NotBlank @Max(100) String name
) {

}
