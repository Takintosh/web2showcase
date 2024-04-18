package com.takintosh.web2showcase.dtos;

import jakarta.validation.constraints.NotBlank;

public record CarRecordDto(
        @NotBlank String carModel,
        @NotBlank String carBrand,
        String carColor,
        @NotBlank String carPlate,
        String carImage
) {

}