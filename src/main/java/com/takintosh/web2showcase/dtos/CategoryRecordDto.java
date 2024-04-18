package com.takintosh.web2showcase.dtos;

import jakarta.validation.constraints.NotBlank;

public record CategoryRecordDto(
        @NotBlank String name
) {

}
