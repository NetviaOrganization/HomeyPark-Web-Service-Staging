package com.homeypark.web_service.reviews.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record UserId(Long userId) {
    public UserId {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User id cannot be null or less than or equal to zero");
        }
    }
}
