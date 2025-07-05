package com.homeypark.web_service.iam.interfaces.rest.resources;

public record UserDiscountInfoResource(
    Long userId,
    boolean hasDiscount,
    Long successfulReservationsCount,
    String discountStatus
) {
    public UserDiscountInfoResource(Long userId, boolean hasDiscount, Long successfulReservationsCount) {
        this(userId, hasDiscount, successfulReservationsCount, 
             hasDiscount ? "Active (10% discount applied)" : 
             successfulReservationsCount >= 10 ? "Eligible (activation needed)" : 
             String.format("Not eligible (%d/10 successful reservations)", successfulReservationsCount));
    }
}
