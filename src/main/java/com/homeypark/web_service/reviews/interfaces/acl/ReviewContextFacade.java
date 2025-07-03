package com.homeypark.web_service.reviews.interfaces.acl;

public interface ReviewContextFacade {
    Double getAverageRatingByParkingId(Long parkingId);
    Long getReviewCountByParkingId(Long parkingId);
}
