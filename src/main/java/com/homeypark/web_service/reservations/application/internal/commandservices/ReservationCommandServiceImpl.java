package com.homeypark.web_service.reservations.application.internal.commandservices;

import com.homeypark.web_service.iam.interfaces.acl.IamContextFacade;
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
import com.homeypark.web_service.reservations.interfaces.acl.ReservationContextFacade;
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
    private final IamContextFacade iamContextFacade;
    private final ReservationContextFacade reservationContextFacade;

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

        // Check if user has discount and apply it to the total fare
        Double finalTotalFare = command.totalFare();
        try {
            boolean hasDiscount = iamContextFacade.getUserHasDiscount(command.guestId());
            if (hasDiscount) {
                // Apply 10% discount
                finalTotalFare = command.totalFare() * 0.9;
            }
        } catch (Exception e) {
            // Log error but don't fail reservation creation
            System.err.println("Failed to check user discount for user " + command.guestId() + ": " + e.getMessage());
        }

        var reservation = new Reservation(command);
        // Override the total fare with discounted amount if applicable
        reservation.setTotalFare(finalTotalFare);
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
        
        // Check if user has discount and apply it to the updated total fare
        Double finalTotalFare = command.totalFare();
        try {
            Long guestId = reservationToUpdate.getGuestId().guestId();
            boolean hasDiscount = iamContextFacade.getUserHasDiscount(guestId);
            if (hasDiscount) {
                // Apply 10% discount
                finalTotalFare = command.totalFare() * 0.9;
            }
        } catch (Exception e) {
            // Log error but don't fail reservation update
            System.err.println("Failed to check user discount for reservation update: " + e.getMessage());
        }
        
        try {
            reservationToUpdate.updatedReservation(command);
            // Override the total fare with discounted amount if applicable
            reservationToUpdate.setTotalFare(finalTotalFare);
            var updatedReservation = reservationRepository.save(reservationToUpdate);
            return Optional.of(updatedReservation);
        } catch (Exception e) {
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
            
            // Check if reservation was just completed and activate discount if user reaches 10 successful reservations
            if (command.status() == Status.Completed) {
                Long guestId = updatedStatus.getGuestId().guestId();
                Long successfulCount = reservationContextFacade.getSuccessfulReservationCountByUserId(guestId);
                
                // If user has reached 10 successful reservations, activate discount
                if (successfulCount >= 10) {
                    try {
                        iamContextFacade.activateUserDiscount(guestId);
                    } catch (Exception e) {
                        // Log error but don't fail the reservation update
                        System.err.println("Failed to activate discount for user " + guestId + ": " + e.getMessage());
                    }
                }
            }
            
            return Optional.of(updatedStatus);
        } catch (Exception e) {
            throw new ReservationUpdateException();
        }
    }
}
