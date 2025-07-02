package com.homeypark.web_service.reservations.domain.services;

import com.homeypark.web_service.reservations.domain.model.aggregates.Reservation;
import com.homeypark.web_service.reservations.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

public interface ReservationQueryService {
    List<Reservation> handle(GetAllReservationsQuery query);
    Optional<Reservation> handle(GetReservationByIdQuery query);
    List<Reservation> handle(GetInProgressReservationQuery query);
    List<Reservation> handle(GetPastReservationQuery query);
    List<Reservation> handle(GetUpComingReservationQuery query);
    List<Reservation> handle(GetReservationsByHostIdQuery query);
    List<Reservation> handle(GetReservationsByGuestIdQuery query);
}
