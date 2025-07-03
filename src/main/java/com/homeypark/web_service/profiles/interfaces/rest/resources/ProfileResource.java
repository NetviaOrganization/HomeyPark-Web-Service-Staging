package com.homeypark.web_service.profiles.interfaces.rest.resources;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ProfileResource(
        Long id,
        String firstName,
        String lastName,
        LocalDate birthDate,
        Long userId,
        boolean verifiedEmail,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
