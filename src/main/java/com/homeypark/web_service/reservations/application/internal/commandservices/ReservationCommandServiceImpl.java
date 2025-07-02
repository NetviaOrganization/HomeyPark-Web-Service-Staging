package com.homeypark.web_service.reservations.application.internal.commandservices;

import com.homeypark.web_service.reservations.application.internal.outboundservices.acl.ExternalParkingService;
import com.homeypark.web_service.reservations.application.internal.outboundservices.acl.ExternalProfileService;
import com.homeypark.web_service.reservations.application.internal.outboundservices.acl.ExternalScheduleService;
import com.homeypark.web_service.reservations.application.internal.outboundservices.acl.ExternalVehicleService;
import com.homeypark.web_service.reservations.domain.model.commands.CreateReservationCommand;
import com.homeypark.web_service.reservations.domain.model.commands.UpdateReservationCommand;
import com.homeypark.web_service.reservations.domain.model.commands.UpdateStatusCommand;
import com.homeypark.web_service.reservations.domain.model.aggregates.Reservation;
import com.homeypark.web_service.reservations.domain.model.exceptions.*;
import com.homeypark.web_service.reservations.domain.model.valueobject.ParkingId;
import com.homeypark.web_service.reservations.domain.model.valueobject.Status;
import com.homeypark.web_service.reservations.domain.services.ReservationCommandService;
import com.homeypark.web_service.reservations.infrastructure.external.ImageUploadService;
import com.homeypark.web_service.reservations.infrastructure.external.ImgbbResponse;
import com.homeypark.web_service.reservations.infrastructure.persistence.jpa.repositories.ReservationRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ReservationCommandServiceImpl implements ReservationCommandService {
    private final ReservationRepository reservationRepository;
    private final ExternalProfileService externalProfileService;
    private final ExternalVehicleService externalVehicleService;
    private final ExternalParkingService externalParkingService;
    private final ImageUploadService imageUploadService;
    private final ExternalScheduleService externalScheduleService;

    @Transactional
    @Override
    public Optional<Reservation> handle(CreateReservationCommand command, MultipartFile file) {
        if (!externalProfileService.checkProfileExistById(command.guestId()) || !externalProfileService.checkProfileExistById(command.hostId())) {
            throw new ProfileNotFoundException();
        }
        if (file.isEmpty()) throw new EmptyFileException();
        if (!externalVehicleService.checkVehicleExistById(command.vehicleId())) throw new VehicleNotFoundException();
        if (!externalParkingService.checkParkingExistById(command.parkingId())) throw new ParkingNotFoundException();

        List<Status> blockedStatuses = List.of(Status.Approved, Status.InProgress, Status.Completed);

        if (reservationRepository.existsByOverlapWithStatus(
                new ParkingId(command.parkingId()),
                command.reservationDate(),
                command.startTime(),
                command.endTime(),
                blockedStatuses)) {
            throw new ReservationOverlapException();
        }


        var reservation = new Reservation(command);
        reservation.setStatus(Status.Pending);
        try {
            ImgbbResponse imgbbResponse = imageUploadService.uploadImage(file).block();

            if (imgbbResponse != null && imgbbResponse.success()) {
                reservation.setPaymentReceiptUrl(imgbbResponse.data().url());
                reservation.setPaymentReceiptDeleteUrl(imgbbResponse.data().deleteUrl());
            } else {
                System.err.println("Error al cargar la imagen o respuesta no exitosa de ImgBB.");
                return Optional.empty();
            }

            var response = reservationRepository.save(reservation);
            return Optional.of(response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    @Transactional
    @Override
    public Optional<Reservation> handle(UpdateReservationCommand command) {
        var result = reservationRepository.findById(command.reservationId());
        if (result.isEmpty())
            throw new ReservationNotFoundException();

        if (!externalScheduleService.doesScheduleEncloseTimeRange(command.reservationDate().getDayOfWeek().name(), command.startTime(), command.endTime())) {
            throw new ScheduleConflictException();
        }

        List<Status> blockedStatuses = List.of(Status.Approved, Status.InProgress, Status.Completed);

        if (reservationRepository.existsByOverlapWithStatus(
                new ParkingId(result.get().getParkingId().parkingId()),
                command.reservationDate(),
                command.startTime(),
                command.endTime(),
                blockedStatuses)) {
            throw new ReservationOverlapException();
        }

        var reservationToUpdate = result.get();
        try{
            var updatedReservation= reservationRepository.save(reservationToUpdate.updatedReservation(command));
            return Optional.of(updatedReservation);
        }catch (Exception e){
            throw new ReservationUpdateException();
        }
    }

    @Override
    public Optional<Reservation> handle(UpdateStatusCommand command) {
        var result = reservationRepository.findById(command.reservationId());
        if (result.isEmpty())
            throw new ReservationNotFoundException();
        var statusToUpdate = result.get();
        try {
            var updatedStatus = reservationRepository.save(statusToUpdate.updatedStatus(command));
            return Optional.of(updatedStatus);
        }catch (Exception e){
            throw new ReservationUpdateException();
        }
    }
}
