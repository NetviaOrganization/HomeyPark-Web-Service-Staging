package com.homeypark.web_service.parkings.interfaces.rest.resources;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record ParkingResource(
        Long id,
        Long profileId,
        Double width,
        Double length,
        Double height,
        Double price,
        String phone,
        Integer space,
        String description,
        LocationResource location,
        List<ScheduleResource> schedules,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
