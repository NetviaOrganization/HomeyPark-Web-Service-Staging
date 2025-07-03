package com.homeypark.web_service.reviews.domain.model.commands;

public record UpdateReviewCommand(
        Long reviewId,
        Integer rating,
        String comment
) {
    public UpdateReviewCommand {
        if (reviewId == null || reviewId <= 0) {
            throw new IllegalArgumentException("Review id cannot be null or less than or equal to zero");
        }
        if (rating == null || rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
    }
}
