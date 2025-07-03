package com.homeypark.web_service.reviews.domain.services;

import com.homeypark.web_service.reviews.domain.model.aggregates.Review;
import com.homeypark.web_service.reviews.domain.model.commands.CreateReviewCommand;
import com.homeypark.web_service.reviews.domain.model.commands.DeleteReviewCommand;
import com.homeypark.web_service.reviews.domain.model.commands.UpdateReviewCommand;

import java.util.Optional;

public interface ReviewCommandService {
    Optional<Review> handle(CreateReviewCommand command);
    Optional<Review> handle(UpdateReviewCommand command);
    void handle(DeleteReviewCommand command);
}
