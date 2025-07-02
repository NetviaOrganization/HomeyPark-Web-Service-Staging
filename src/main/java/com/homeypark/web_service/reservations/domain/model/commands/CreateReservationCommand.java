package com.homeypark.web_service.reservations.domain.model.commands;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateReservationCommand(
        Integer hoursRegistered,
        Double totalFare,
        LocalDate reservationDate,
        LocalTime startTime,
        LocalTime endTime,
        Long guestId,
        Long hostId,
        Long parkingId,
        Long vehicleId
) {}
