package com.homeypark.web_service.reviews.domain.model.commands;

public record DeleteReviewCommand(Long reviewId) {
    public DeleteReviewCommand {
        if (reviewId == null || reviewId <= 0) {
            throw new IllegalArgumentException("Review id cannot be null or less than or equal to zero");
        }
    }
}
