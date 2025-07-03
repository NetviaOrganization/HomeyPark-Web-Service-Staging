package com.homeypark.web_service.reviews.application.internal.queryservices;

import com.homeypark.web_service.reviews.domain.model.aggregates.Review;
import com.homeypark.web_service.reviews.domain.model.queries.*;
import com.homeypark.web_service.reviews.domain.model.valueobjects.ParkingId;
import com.homeypark.web_service.reviews.domain.model.valueobjects.UserId;
import com.homeypark.web_service.reviews.domain.services.ReviewQueryService;
import com.homeypark.web_service.reviews.infrastructure.persistence.repositories.jpa.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewQueryServiceImpl implements ReviewQueryService {
    private final ReviewRepository reviewRepository;

    public ReviewQueryServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> handle(GetAllReviewsQuery query) {
        return reviewRepository.findAll();
    }

    @Override
    public Optional<Review> handle(GetReviewByIdQuery query) {
        return reviewRepository.findById(query.reviewId());
    }

    @Override
    public List<Review> handle(GetReviewsByParkingIdQuery query) {
        return reviewRepository.findByParkingId(new ParkingId(query.parkingId()));
    }

    @Override
    public List<Review> handle(GetReviewsByUserIdQuery query) {
        return reviewRepository.findByUserId(new UserId(query.userId()));
    }

    @Override
    public Double handle(GetAverageRatingByParkingIdQuery query) {
        return reviewRepository.findAverageRatingByParkingId(new ParkingId(query.parkingId()));
    }
}
