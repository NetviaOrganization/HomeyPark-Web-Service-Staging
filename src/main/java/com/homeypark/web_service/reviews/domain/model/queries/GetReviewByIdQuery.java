package com.homeypark.web_service.reviews.domain.model.queries;

public record GetReviewByIdQuery(Long reviewId) {
    public GetReviewByIdQuery {
        if (reviewId == null || reviewId <= 0) {
            throw new IllegalArgumentException("Review id cannot be null or less than or equal to zero");
        }
    }
}
