package com.homeypark.web_service.reviews.application.acl;

import com.homeypark.web_service.reviews.domain.model.queries.GetAverageRatingByParkingIdQuery;
import com.homeypark.web_service.reviews.domain.model.valueobjects.ParkingId;
import com.homeypark.web_service.reviews.domain.services.ReviewQueryService;
import com.homeypark.web_service.reviews.infrastructure.persistence.repositories.jpa.ReviewRepository;
import com.homeypark.web_service.reviews.interfaces.acl.ReviewContextFacade;
import org.springframework.stereotype.Service;

@Service
public class ReviewContextFacadeImpl implements ReviewContextFacade {
    private final ReviewQueryService reviewQueryService;
    private final ReviewRepository reviewRepository;

    public ReviewContextFacadeImpl(ReviewQueryService reviewQueryService, ReviewRepository reviewRepository) {
        this.reviewQueryService = reviewQueryService;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Double getAverageRatingByParkingId(Long parkingId) {
        var query = new GetAverageRatingByParkingIdQuery(parkingId);
        Double average = reviewQueryService.handle(query);
        return average != null ? average : 0.0;
    }

    @Override
    public Long getReviewCountByParkingId(Long parkingId) {
        return reviewRepository.countReviewsByParkingId(new ParkingId(parkingId));
    }
}
