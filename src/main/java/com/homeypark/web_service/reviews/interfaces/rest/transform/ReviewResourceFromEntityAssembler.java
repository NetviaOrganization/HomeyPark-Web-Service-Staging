package com.homeypark.web_service.reviews.interfaces.rest.transform;

import com.homeypark.web_service.reviews.domain.model.aggregates.Review;
import com.homeypark.web_service.reviews.interfaces.rest.resources.ReviewResource;

public class ReviewResourceFromEntityAssembler {
    public static ReviewResource toResourceFromEntity(Review entity) {
        return new ReviewResource(
                entity.getId(),
                entity.getRating(),
                entity.getComment(),
                entity.getParkingIdValue(),
                entity.getUserIdValue(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
