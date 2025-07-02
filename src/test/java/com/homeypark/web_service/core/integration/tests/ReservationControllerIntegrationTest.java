package com.homeypark.web_service.core.integration.tests;

import com.homeypark.web_service.reservations.domain.model.exceptions.ReservationNotFoundException;
import com.homeypark.web_service.reservations.domain.model.aggregates.Reservation;
import com.homeypark.web_service.reservations.domain.model.commands.UpdateReservationCommand;
import com.homeypark.web_service.reservations.domain.model.commands.UpdateStatusCommand;
import com.homeypark.web_service.reservations.domain.model.queries.*;
import com.homeypark.web_service.reservations.domain.model.valueobject.*;
import com.homeypark.web_service.reservations.domain.services.ReservationCommandService;
import com.homeypark.web_service.reservations.domain.services.ReservationQueryService;
import com.homeypark.web_service.reservations.interfaces.rest.ReservationController;
import com.homeypark.web_service.reservations.interfaces.rest.resources.CreateReservationResource;
import com.homeypark.web_service.reservations.interfaces.rest.resources.ReservationResource;
import com.homeypark.web_service.reservations.interfaces.rest.resources.UpdateReservationResource;
import com.homeypark.web_service.reservations.interfaces.rest.resources.UpdateStatusResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

public class ReservationControllerIntegrationTest {
    private ReservationCommandService reservationCommandService;
    private ReservationQueryService reservationQueryService;
    private ReservationController reservationController;

    @BeforeEach
    void setUp() {
        reservationCommandService = Mockito.mock(ReservationCommandService.class);
        reservationQueryService = Mockito.mock(ReservationQueryService.class);
        reservationController = new ReservationController(reservationCommandService, reservationQueryService);
    }

    private Reservation createMockReservation(Long id, Status status, LocalDate date, LocalTime startTime,
                                              LocalTime endTime, Double totalFare, int hoursRegistered,
                                              Long hostId, Long guestId, Long parkingId, Long vehicleId) {
        Reservation reservation = new Reservation();
        reservation.setId(id);
        reservation.setStatus(status);
        reservation.setReservationDate(date);
        reservation.setStartTime(startTime);
        reservation.setEndTime(endTime);
        reservation.setTotalFare(totalFare);
        reservation.setHoursRegistered(hoursRegistered);
        reservation.setHostId(new HostId(hostId));
        reservation.setGuestId(new GuestId(guestId));
        reservation.setParkingId(new ParkingId(parkingId));
        reservation.setVehicleId(new VehicleId(vehicleId));
        reservation.setPaymentReceiptUrl("http://example.com/receipt" + id);
        reservation.setPaymentReceiptDeleteUrl("http://example.com/delete" + id);
        return reservation;
    }

    private void verifyResourceMatchesReservation(ReservationResource resource, Reservation reservation) {
        assertEquals(reservation.getId(), resource.id());
        assertEquals(reservation.getStatus().name(), resource.status());
        assertEquals(reservation.getReservationDate(), resource.reservationDate());
        assertEquals(reservation.getStartTime(), resource.startTime());
        assertEquals(reservation.getEndTime(), resource.endTime());
        assertEquals(reservation.getTotalFare(), resource.totalFare());
        assertEquals(reservation.getHoursRegistered(), resource.hoursRegistered());
        assertEquals(reservation.getHostId().hostId(), resource.hostId());
        assertEquals(reservation.getGuestId().guestId(), resource.guestId());
        assertEquals(reservation.getParkingId().parkingId(), resource.parkingId());
        assertEquals(reservation.getVehicleId().vehicleId(), resource.vehicleId());
        assertEquals(reservation.getPaymentReceiptUrl(), resource.paymentReceiptUrl());
    }

    @Test
    void testGetAllReservationsSuccess() {
        Reservation mockReservation = createMockReservation(1L, Status.Pending,
                LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(12, 0),
                20.0, 2, 1L, 2L, 1L, 1L);

        Mockito.when(reservationQueryService.handle(ArgumentMatchers.any(GetAllReservationsQuery.class)))
                .thenReturn(Collections.singletonList(mockReservation));

        ResponseEntity<List<ReservationResource>> response = reservationController.getAllReservations();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());

        verifyResourceMatchesReservation(response.getBody().get(0), mockReservation);

        verify(reservationQueryService).handle((GetAllReservationsQuery) ArgumentMatchers.argThat(query ->
                query instanceof GetAllReservationsQuery));
    }

    @Test
    void testCreateReservationSuccess() {
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test image content".getBytes());
        CreateReservationResource resource = new CreateReservationResource(
                2, 20.0, LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(12, 0), 1L, 2L, 3L, 4L
        );

        Reservation mockReservation = createMockReservation(1L, Status.Pending,
                LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(12, 0),
                20.0, 2, 1L, 2L, 3L, 4L);

        Mockito.when(reservationCommandService.handle(ArgumentMatchers.any(), ArgumentMatchers.any(MockMultipartFile.class)))
                .thenReturn(Optional.of(mockReservation));

        ResponseEntity<ReservationResource> response = reservationController.createReservation(file, resource);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());

        verifyResourceMatchesReservation(response.getBody(), mockReservation);
    }

    @Test
    void testCreateReservationBadRequest() {
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test image content".getBytes());
        CreateReservationResource resource = new CreateReservationResource(
                2, 20.0, LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(12, 0), 1L, 2L, 3L, 4L
        );

        Mockito.when(reservationCommandService.handle(ArgumentMatchers.any(), ArgumentMatchers.any(MockMultipartFile.class)))
                .thenReturn(Optional.empty());

        ResponseEntity<ReservationResource> response = reservationController.createReservation(file, resource);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testUpdateReservationSuccess() {
        Long reservationId = 1L;
        UpdateReservationResource resource = new UpdateReservationResource(
                3, 30.0, LocalDate.now().plusDays(1), LocalTime.of(11, 0), LocalTime.of(13, 0)
        );
        Reservation mockReservation = createMockReservation(reservationId, Status.Pending,
                LocalDate.now().plusDays(1), LocalTime.of(11, 0), LocalTime.of(13, 0),
                30.0, 3, 1L, 2L, 1L, 1L);

        Mockito.when(reservationCommandService.handle(ArgumentMatchers.any(UpdateReservationCommand.class)))
                .thenReturn(Optional.of(mockReservation));

        ResponseEntity<ReservationResource> response = reservationController.updateReservation(reservationId, resource);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        verifyResourceMatchesReservation(response.getBody(), mockReservation);

        ArgumentCaptor<UpdateReservationCommand> commandCaptor = ArgumentCaptor.forClass(UpdateReservationCommand.class);
        verify(reservationCommandService).handle(commandCaptor.capture());
        assertEquals(reservationId, commandCaptor.getValue().reservationId());
    }

    @Test
    void testUpdateReservationNotFound() {
        Long reservationId = 1L;
        UpdateReservationResource resource = new UpdateReservationResource(
                3, 30.0, LocalDate.now().plusDays(1), LocalTime.of(11, 0), LocalTime.of(13, 0)
        );

        Mockito.when(reservationCommandService.handle(ArgumentMatchers.any(UpdateReservationCommand.class)))
                .thenReturn(Optional.empty());

        ResponseEntity<ReservationResource> response = reservationController.updateReservation(reservationId, resource);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testUpdateReservationStatusSuccess() {
        Long reservationId = 1L;
        UpdateStatusResource resource = new UpdateStatusResource(Status.Approved);
        Reservation mockReservation = createMockReservation(reservationId, Status.Approved,
                LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(12, 0),
                20.0, 2, 1L, 2L, 1L, 1L);

        Mockito.when(reservationCommandService.handle(ArgumentMatchers.any(UpdateStatusCommand.class)))
                .thenReturn(Optional.of(mockReservation));

        ResponseEntity<ReservationResource> response = reservationController.updateReservationStatus(reservationId, resource);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        verifyResourceMatchesReservation(response.getBody(), mockReservation);

        ArgumentCaptor<UpdateStatusCommand> commandCaptor = ArgumentCaptor.forClass(UpdateStatusCommand.class);
        verify(reservationCommandService).handle(commandCaptor.capture());
        assertEquals(reservationId, commandCaptor.getValue().reservationId());
        assertEquals(Status.Approved, commandCaptor.getValue().status());
    }

    @Test
    void testUpdateReservationStatusNotFound() {
        Long reservationId = 1L;
        UpdateStatusResource resource = new UpdateStatusResource(Status.Approved);

        Mockito.when(reservationCommandService.handle(ArgumentMatchers.any(UpdateStatusCommand.class)))
                .thenReturn(Optional.empty());

        ResponseEntity<ReservationResource> response = reservationController.updateReservationStatus(reservationId, resource);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetReservationByIdSuccess() {
        Long reservationId = 1L;
        Reservation mockReservation = createMockReservation(reservationId, Status.Cancelled,
                LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(12, 0),
                20.0, 2, 1L, 2L, 1L, 1L);

        Mockito.when(reservationQueryService.handle(ArgumentMatchers.any(GetReservationByIdQuery.class)))
                .thenReturn(Optional.of(mockReservation));

        ResponseEntity<ReservationResource> response = reservationController.getReservationById(reservationId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        verifyResourceMatchesReservation(response.getBody(), mockReservation);

        ArgumentCaptor<GetReservationByIdQuery> queryCaptor = ArgumentCaptor.forClass(GetReservationByIdQuery.class);
        verify(reservationQueryService).handle(queryCaptor.capture());
        assertEquals(reservationId, queryCaptor.getValue().reservationId());
    }

    @Test
    void testGetReservationByIdNotFound() {
        Long reservationId = 1L;
        Mockito.when(reservationQueryService.handle(ArgumentMatchers.any(GetReservationByIdQuery.class)))
                .thenThrow(new ReservationNotFoundException());

        assertThrows(ReservationNotFoundException.class, () -> {
            reservationController.getReservationById(reservationId);
        });
    }

    @Test
    void testGetReservationsByHostIdSuccess() {
        Long hostId = 1L;
        Reservation mockReservation = createMockReservation(1L, Status.Pending,
                LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(12, 0),
                20.0, 2, hostId, 2L, 1L, 1L);

        Mockito.when(reservationQueryService.handle(ArgumentMatchers.any(GetReservationsByHostIdQuery.class)))
                .thenReturn(Collections.singletonList(mockReservation));

        ResponseEntity<List<ReservationResource>> response = reservationController.getReservationsByHostId(hostId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());

        assertEquals(hostId, response.getBody().get(0).hostId());

        ArgumentCaptor<GetReservationsByHostIdQuery> queryCaptor = ArgumentCaptor.forClass(GetReservationsByHostIdQuery.class);
        verify(reservationQueryService).handle(queryCaptor.capture());
        assertEquals(hostId, queryCaptor.getValue().hostId());
    }

    @Test
    void testGetReservationsByGuestIdSuccess() {
        Long guestId = 2L;
        Reservation mockReservation = createMockReservation(1L, Status.Pending,
                LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(12, 0),
                20.0, 2, 1L, guestId, 1L, 1L);

        Mockito.when(reservationQueryService.handle(ArgumentMatchers.any(GetReservationsByGuestIdQuery.class)))
                .thenReturn(Collections.singletonList(mockReservation));

        ResponseEntity<List<ReservationResource>> response = reservationController.getReservationsByGuestId(guestId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());

        assertEquals(guestId, response.getBody().get(0).guestId());

        ArgumentCaptor<GetReservationsByGuestIdQuery> queryCaptor = ArgumentCaptor.forClass(GetReservationsByGuestIdQuery.class);
        verify(reservationQueryService).handle(queryCaptor.capture());
        assertEquals(guestId, queryCaptor.getValue().guestId());
    }

    @Test
    void testGetInProgressReservationsSuccess() {
        Reservation mockReservation = createMockReservation(1L, Status.InProgress,
                LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(12, 0),
                20.0, 2, 1L, 2L, 1L, 1L);

        Mockito.when(reservationQueryService.handle(ArgumentMatchers.any(GetInProgressReservationQuery.class)))
                .thenReturn(Collections.singletonList(mockReservation));

        ResponseEntity<List<ReservationResource>> response = reservationController.getInProgressReservation();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());

        verifyResourceMatchesReservation(response.getBody().get(0), mockReservation);
        assertEquals(Status.InProgress.name(), response.getBody().get(0).status());

        verify(reservationQueryService).handle(ArgumentMatchers.isA(GetInProgressReservationQuery.class));
    }

    @Test
    void testGetUpComingReservationsSuccess() {
        Reservation pendingReservation = createMockReservation(1L, Status.Pending,
                LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(12, 0),
                20.0, 2, 1L, 2L, 1L, 1L);

        Reservation approvedReservation = createMockReservation(2L, Status.Approved,
                LocalDate.now().plusDays(1), LocalTime.of(14, 0), LocalTime.of(16, 0),
                30.0, 2, 1L, 3L, 2L, 3L);

        List<Reservation> upcomingReservations = List.of(pendingReservation, approvedReservation);

        Mockito.when(reservationQueryService.handle(ArgumentMatchers.any(GetUpComingReservationQuery.class)))
                .thenReturn(upcomingReservations);

        ResponseEntity<List<ReservationResource>> response = reservationController.getUpComingReservation();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());

        boolean hasPendingStatus = response.getBody().stream()
                .anyMatch(resource -> resource.status().equals(Status.Pending.name()));
        boolean hasApprovedStatus = response.getBody().stream()
                .anyMatch(resource -> resource.status().equals(Status.Approved.name()));

        assertTrue(hasPendingStatus);
        assertTrue(hasApprovedStatus);

        List<Long> reservationIds = response.getBody().stream()
                .map(ReservationResource::id)
                .toList();
        assertTrue(reservationIds.contains(1L));
        assertTrue(reservationIds.contains(2L));

        verify(reservationQueryService).handle(ArgumentMatchers.isA(GetUpComingReservationQuery.class));
    }

    @Test
    void testGetPastReservationsSuccess() {
        Reservation completedReservation = createMockReservation(1L, Status.Completed,
                LocalDate.now().minusDays(2), LocalTime.of(10, 0), LocalTime.of(12, 0),
                20.0, 2, 1L, 2L, 1L, 1L);

        Reservation cancelledReservation = createMockReservation(2L, Status.Cancelled,
                LocalDate.now().minusDays(1), LocalTime.of(14, 0), LocalTime.of(16, 0),
                30.0, 2, 3L, 4L, 2L, 2L);

        List<Reservation> pastReservations = List.of(completedReservation, cancelledReservation);

        Mockito.when(reservationQueryService.handle(ArgumentMatchers.any(GetPastReservationQuery.class)))
                .thenReturn(pastReservations);

        ResponseEntity<List<ReservationResource>> response = reservationController.getPastReservations();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());

        boolean hasCompletedStatus = response.getBody().stream()
                .anyMatch(resource -> resource.status().equals(Status.Completed.name()));
        boolean hasCancelledStatus = response.getBody().stream()
                .anyMatch(resource -> resource.status().equals(Status.Cancelled.name()));

        assertTrue(hasCompletedStatus);
        assertTrue(hasCancelledStatus);

        List<Long> reservationIds = response.getBody().stream()
                .map(ReservationResource::id)
                .toList();
        assertTrue(reservationIds.contains(1L));
        assertTrue(reservationIds.contains(2L));

        verify(reservationQueryService).handle(ArgumentMatchers.isA(GetPastReservationQuery.class));
    }

    @Test
    void testGetReservationsEmptyList() {
        Mockito.when(reservationQueryService.handle(ArgumentMatchers.any(GetAllReservationsQuery.class)))
                .thenReturn(Collections.emptyList());

        ResponseEntity<List<ReservationResource>> response = reservationController.getAllReservations();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());

        verify(reservationQueryService).handle((GetAllReservationsQuery) ArgumentMatchers.argThat(query ->
                query instanceof GetAllReservationsQuery));
    }
}