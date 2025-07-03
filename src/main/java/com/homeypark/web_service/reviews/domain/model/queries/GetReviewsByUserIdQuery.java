package com.homeypark.web_service.reviews.domain.model.queries;

public record GetReviewsByUserIdQuery(Long userId) {
    public GetReviewsByUserIdQuery {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User id cannot be null or less than or equal to zero");
        }
    }
}
