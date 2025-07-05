package com.homeypark.web_service.reservations.interfaces.acl;

public interface ReservationContextFacade {
    Long getSuccessfulReservationCountByUserId(Long userId);
}
