package com.homeypark.web_service.vehicles.interfaces.rest.resources;


import java.time.LocalDateTime;

public record VehicleResource(
        Long id,
        String licensePlate,
        String model,
        String brand,
        String profileId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
