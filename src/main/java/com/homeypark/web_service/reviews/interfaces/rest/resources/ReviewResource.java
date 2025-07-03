package com.homeypark.web_service.reviews.interfaces.rest.resources;

import java.time.LocalDateTime;

public record ReviewResource(
        Long id,
        Integer rating,
        String comment,
        Long parkingId,
        Long userId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
