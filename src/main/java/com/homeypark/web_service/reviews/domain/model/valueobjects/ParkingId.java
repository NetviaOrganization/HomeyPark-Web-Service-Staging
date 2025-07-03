package com.homeypark.web_service.reviews.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record ParkingId(Long parkingId) {
    public ParkingId {
        if (parkingId == null || parkingId <= 0) {
            throw new IllegalArgumentException("Parking id cannot be null or less than or equal to zero");
        }
    }
}
