package com.homeypark.web_service.reviews.domain.services;

import com.homeypark.web_service.reviews.domain.model.aggregates.Review;
import com.homeypark.web_service.reviews.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

public interface ReviewQueryService {
    List<Review> handle(GetAllReviewsQuery query);
    Optional<Review> handle(GetReviewByIdQuery query);
    List<Review> handle(GetReviewsByParkingIdQuery query);
    List<Review> handle(GetReviewsByUserIdQuery query);
    Double handle(GetAverageRatingByParkingIdQuery query);
}
