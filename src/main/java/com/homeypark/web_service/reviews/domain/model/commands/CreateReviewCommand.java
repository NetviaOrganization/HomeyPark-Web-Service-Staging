package com.homeypark.web_service.reviews.domain.model.commands;

public record CreateReviewCommand(
        Integer rating,
        String comment,
        Long parkingId,
        Long userId
) {
    public CreateReviewCommand {
        if (rating == null || rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        if (parkingId == null || parkingId <= 0) {
            throw new IllegalArgumentException("Parking id cannot be null or less than or equal to zero");
        }
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User id cannot be null or less than or equal to zero");
        }
    }
}
