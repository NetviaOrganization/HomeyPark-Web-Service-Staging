package com.homeypark.web_service.reservations.domain.model.valueobject;

import jakarta.persistence.Embeddable;

@Embeddable
public record GuestId(Long guestId) {
    public GuestId {
        if (guestId < 0) {
            throw new IllegalArgumentException("Guest guestId cannot be negative");
        }
    }

    public GuestId() { this(0L); }
}
