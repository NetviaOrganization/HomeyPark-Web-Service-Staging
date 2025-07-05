package com.homeypark.web_service.reservations.domain.model.queries;

public record GetSuccessfulReservationCountByUserIdQuery(Long userId) {
    public GetSuccessfulReservationCountByUserIdQuery {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User id cannot be null or less than or equal to zero");
        }
    }
}
