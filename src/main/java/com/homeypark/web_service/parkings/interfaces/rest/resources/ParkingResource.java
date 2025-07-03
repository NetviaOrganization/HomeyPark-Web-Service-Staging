package com.homeypark.web_service.parkings.interfaces.rest.resources;

import com.homeypark.web_service.shared.interfaces.rest.resources.UserInfoResource;

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
        UserInfoResource userInfo,
        Double averageRating,
        Long reviewCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
