package com.homeypark.web_service.reviews.domain.model.queries;

public record GetAverageRatingByParkingIdQuery(Long parkingId) {
    public GetAverageRatingByParkingIdQuery {
        if (parkingId == null || parkingId <= 0) {
            throw new IllegalArgumentException("Parking id cannot be null or less than or equal to zero");
        }
    }
}
