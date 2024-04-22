package com.takintosh.web2showcase.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CarRecordDto(
        @NotBlank String carModel,
        @NotBlank String carBrand,
        String carColor,
        @NotBlank String carPlate,
        String carImage,
        @NotNull UUID categoryId
) {

}