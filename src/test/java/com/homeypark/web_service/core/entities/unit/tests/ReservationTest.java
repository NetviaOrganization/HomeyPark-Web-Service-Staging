package com.homeypark.web_service.core.entities.unit.tests;

import com.homeypark.web_service.reservations.domain.model.aggregates.Reservation;
import com.homeypark.web_service.reservations.domain.model.commands.CreateReservationCommand;
import com.homeypark.web_service.reservations.domain.model.commands.UpdateReservationCommand;
import com.homeypark.web_service.reservations.domain.model.commands.UpdateStatusCommand;
import com.homeypark.web_service.reservations.domain.model.valueobject.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReservationTest {
    private CreateReservationCommand createCommand;
    private UpdateReservationCommand updateCommand;
    private UpdateStatusCommand updateStatusCommand;

    private final Long hostId = 1L;
    private final Long guestId = 2L;
    private final Long parkingId = 3L;
    private final Long vehicleId = 4L;
    private final LocalDate reservationDate = LocalDate.now();
    private final LocalTime startTime = LocalTime.of(10, 0);
    private final LocalTime endTime = LocalTime.of(12, 0);
    private final Integer hoursRegistered = 2;
    private final Double totalFare = 20.0;


    @BeforeEach
    void setUp() {
        // Mock CreateReservationCommand
        createCommand = mock(CreateReservationCommand.class);
        when(createCommand.hoursRegistered()).thenReturn(hoursRegistered);
        when(createCommand.totalFare()).thenReturn(totalFare);
        when(createCommand.reservationDate()).thenReturn(reservationDate);
        when(createCommand.startTime()).thenReturn(startTime);
        when(createCommand.endTime()).thenReturn(endTime);
        when(createCommand.hostId()).thenReturn(hostId);
        when(createCommand.guestId()).thenReturn(guestId);
        when(createCommand.parkingId()).thenReturn(parkingId);
        when(createCommand.vehicleId()).thenReturn(vehicleId);

        // Mock UpdateReservationCommand
        updateCommand = mock(UpdateReservationCommand.class);
        when(updateCommand.hoursRegistered()).thenReturn(hoursRegistered + 1);
        when(updateCommand.totalFare()).thenReturn(totalFare + 5.0);
        when(updateCommand.reservationDate()).thenReturn(reservationDate.plusDays(1));
        when(updateCommand.startTime()).thenReturn(startTime.plusHours(1));
        when(updateCommand.endTime()).thenReturn(endTime.plusHours(1));

        // Mock UpdateStatusCommand
        updateStatusCommand = mock(UpdateStatusCommand.class);
        when(updateStatusCommand.status()).thenReturn(Status.Approved);
    }

    @Test
    void testConstructorWithCreateCommand() {
        // Act
        Reservation reservation = new Reservation(createCommand);

        // Assert
        assertNotNull(reservation);
        assertEquals(hoursRegistered, reservation.getHoursRegistered());
        assertEquals(totalFare, reservation.getTotalFare());
        assertEquals(reservationDate, reservation.getReservationDate());
        assertEquals(startTime, reservation.getStartTime());
        assertEquals(endTime, reservation.getEndTime());
        assertEquals(new HostId(hostId), reservation.getHostId());
        assertEquals(new GuestId(guestId), reservation.getGuestId());
        assertEquals(new ParkingId(parkingId), reservation.getParkingId());
        assertEquals(new VehicleId(vehicleId), reservation.getVehicleId());
        assertEquals(Status.Pending, reservation.getStatus()); // Default status
        assertNull(reservation.getCreatedAt()); // Timestamps are set by JPA
        assertNull(reservation.getUpdatedAt());
    }

    @Test
    void testUpdatedReservation() {
        // Arrange
        Reservation reservation = new Reservation(createCommand);

        // Act
        Reservation updatedReservation = reservation.updatedReservation(updateCommand);

        // Assert
        assertNotNull(updatedReservation);
        assertSame(reservation, updatedReservation, "Should return the same instance");
        assertEquals(hoursRegistered + 1, updatedReservation.getHoursRegistered());
        assertEquals(totalFare + 5.0, updatedReservation.getTotalFare());
        assertEquals(reservationDate.plusDays(1), updatedReservation.getReservationDate());
        assertEquals(startTime.plusHours(1), updatedReservation.getStartTime());
        assertEquals(endTime.plusHours(1), updatedReservation.getEndTime());
    }

    @Test
    void testUpdatedStatus() {
        // Arrange
        Reservation reservation = new Reservation(createCommand);

        // Act
        Reservation reservationWithUpdatedStatus = reservation.updatedStatus(updateStatusCommand);

        // Assert
        assertNotNull(reservationWithUpdatedStatus);
        assertSame(reservation, reservationWithUpdatedStatus, "Should return the same instance");
        assertEquals(Status.Approved, reservationWithUpdatedStatus.getStatus());
    }

    @Test
    void testDefaultConstructor() {
        // Act
        Reservation reservation = new Reservation();

        // Assert
        assertNotNull(reservation);
        assertNull(reservation.getId());
        assertNull(reservation.getHoursRegistered());
        assertNull(reservation.getTotalFare());
        assertNull(reservation.getReservationDate());
        assertNull(reservation.getStartTime());
        assertNull(reservation.getEndTime());
        assertNull(reservation.getStatus());
        assertNull(reservation.getGuestId());
        assertNull(reservation.getHostId());
        assertNull(reservation.getParkingId());
        assertNull(reservation.getVehicleId());
    }
}
