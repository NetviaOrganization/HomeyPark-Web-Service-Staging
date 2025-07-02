package com.homeypark.web_service.vehicles.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateVehicleResource(
        @NotBlank(message = "{vehicle.licensePlate.not.blank}")
        @Pattern(regexp = "^[A-Z0-9]{6,8}$", message = "{vehicle.licensePlate.pattern}")
        String licensePlate,

        @NotBlank(message = "{vehicle.model.not.blank}")
        @Size(max = 100, message = "{vehicle.model.size}")
        String model,

        @NotBlank(message = "{vehicle.brand.not.blank}")
        @Size(max = 100, message = "{vehicle.brand.size}")
        String brand
) {
}
