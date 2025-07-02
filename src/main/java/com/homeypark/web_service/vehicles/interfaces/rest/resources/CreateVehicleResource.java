package com.homeypark.web_service.vehicles.interfaces.rest.resources;

import jakarta.validation.constraints.*;

public record CreateVehicleResource(
        @NotNull(message = "{vehicle.licensePlate.not.null}")
        @NotBlank(message = "{vehicle.licensePlate.not.blank}")
        @Pattern(regexp = "^[A-Z0-9]{6,8}$", message = "{vehicle.licensePlate.pattern}")
        String licensePlate,

        @NotNull(message = "{vehicle.model.not.null}")
        @NotBlank(message = "{vehicle.model.not.blank}")
        @Size(max = 100, message = "{vehicle.model.size}")
        String model,

        @NotNull(message = "{vehicle.brand.not.null}")
        @NotBlank(message = "{vehicle.brand.not.blank}")
        @Size(max = 100, message = "{vehicle.brand.size}")
        String brand,

        @NotNull(message = "{vehicle.profileId.not.null}")
        Long profileId
) {}