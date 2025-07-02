package com.homeypark.web_service.reservations.domain.services;

import com.homeypark.web_service.reservations.domain.model.commands.CreateReservationCommand;
import com.homeypark.web_service.reservations.domain.model.commands.UpdateReservationCommand;
import com.homeypark.web_service.reservations.domain.model.commands.UpdateStatusCommand;
import com.homeypark.web_service.reservations.domain.model.aggregates.Reservation;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface ReservationCommandService {
    Optional<Reservation> handle(CreateReservationCommand command, MultipartFile file);
    Optional<Reservation> handle(UpdateReservationCommand command);
    Optional<Reservation> handle(UpdateStatusCommand command);
}
