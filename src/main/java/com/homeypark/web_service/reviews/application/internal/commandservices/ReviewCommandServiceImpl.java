package com.homeypark.web_service.reviews.application.internal.commandservices;

import com.homeypark.web_service.reviews.domain.model.aggregates.Review;
import com.homeypark.web_service.reviews.domain.model.commands.CreateReviewCommand;
import com.homeypark.web_service.reviews.domain.model.commands.DeleteReviewCommand;
import com.homeypark.web_service.reviews.domain.model.commands.UpdateReviewCommand;
import com.homeypark.web_service.reviews.domain.services.ReviewCommandService;
import com.homeypark.web_service.reviews.infrastructure.persistence.repositories.jpa.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewCommandServiceImpl implements ReviewCommandService {
    private final ReviewRepository reviewRepository;

    public ReviewCommandServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Optional<Review> handle(CreateReviewCommand command) {
        var review = new Review(command);
        reviewRepository.save(review);
        return Optional.of(review);
    }

    @Override
    public Optional<Review> handle(UpdateReviewCommand command) {
        var result = reviewRepository.findById(command.reviewId());
        if (result.isEmpty()) {
            return Optional.empty();
        }
        var reviewToUpdate = result.get();
        try {
            var updatedReview = reviewToUpdate.updateReview(command);
            reviewRepository.save(updatedReview);
            return Optional.of(updatedReview);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public void handle(DeleteReviewCommand command) {
        reviewRepository.deleteById(command.reviewId());
    }
}
