package com.homeypark.web_service.core.integration.tests;

import com.homeypark.web_service.parkings.domain.model.aggregates.Parking;
import com.homeypark.web_service.parkings.domain.model.commands.CreateScheduleCommand;
import com.homeypark.web_service.parkings.domain.model.commands.UpdateScheduleCommand;
import com.homeypark.web_service.parkings.domain.model.entities.Schedule;
import com.homeypark.web_service.parkings.domain.model.queries.GetAllScheduleQuery;
import com.homeypark.web_service.parkings.domain.model.valueobjects.WeekDay;
import com.homeypark.web_service.parkings.domain.services.ScheduleCommandService;
import com.homeypark.web_service.parkings.domain.services.ScheduleQueryService;
import com.homeypark.web_service.parkings.interfaces.rest.ScheduleController;
import com.homeypark.web_service.parkings.interfaces.rest.resources.CreateScheduleResource;
import com.homeypark.web_service.parkings.interfaces.rest.resources.UpdateScheduleResource;
import com.homeypark.web_service.parkings.interfaces.rest.resources.ScheduleResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.ArgumentMatchers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ScheduleControllerIntegrationTest {

    private ScheduleCommandService scheduleCommandService;
    private ScheduleQueryService scheduleQueryService;
    private ScheduleController scheduleController;

    @BeforeEach
    void setUp() {
        scheduleCommandService = Mockito.mock(ScheduleCommandService.class);
        scheduleQueryService = Mockito.mock(ScheduleQueryService.class);
        scheduleController = new ScheduleController(scheduleCommandService, scheduleQueryService);
    }

    @Test
    void testCreateScheduleSuccess() {
        // Arrange
        CreateScheduleResource resource = new CreateScheduleResource(
                1L, "MONDAY", LocalTime.of(8, 0), LocalTime.of(18, 0)
        );

        Parking parking = new Parking();
        parking.setId(1L);

        Schedule schedule = new Schedule();
        schedule.setId(1L);
        schedule.setDay(WeekDay.MONDAY);
        schedule.setStartTime(LocalTime.of(8, 0));
        schedule.setEndTime(LocalTime.of(18, 0));
        schedule.setParking(parking);

        Mockito.when(scheduleCommandService.handle(ArgumentMatchers.any(CreateScheduleCommand.class)))
                .thenReturn(Optional.of(schedule));

        // Act
        ResponseEntity<ScheduleResource> response = scheduleController.createSchedule(resource);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("MONDAY", response.getBody().day());
    }

    @Test
    void testUpdateScheduleSuccess() {
        // Arrange
        Long scheduleId = 1L;
        UpdateScheduleResource resource = new UpdateScheduleResource(
                "TUESDAY", LocalTime.of(9, 0), LocalTime.of(17, 0)
        );

        Parking parking = new Parking();
        parking.setId(1L);

        Schedule updated = new Schedule();
        updated.setId(scheduleId);
        updated.setDay(WeekDay.TUESDAY);
        updated.setStartTime(LocalTime.of(9, 0));
        updated.setEndTime(LocalTime.of(17, 0));
        updated.setParking(parking);

        Mockito.when(scheduleCommandService.handle(ArgumentMatchers.any(UpdateScheduleCommand.class)))
                .thenReturn(Optional.of(updated));

        // Act
        ResponseEntity<ScheduleResource> response = scheduleController.updateSchedule(scheduleId, resource);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("TUESDAY", response.getBody().day());
    }

    @Test
    void testGetAllSchedulesSuccess() {
        // Arrange
        Parking parking = new Parking();
        parking.setId(1L);

        Schedule schedule = new Schedule();
        schedule.setId(1L);
        schedule.setDay(WeekDay.FRIDAY);
        schedule.setStartTime(LocalTime.of(10, 0));
        schedule.setEndTime(LocalTime.of(20, 0));
        schedule.setParking(parking);

        Mockito.when(scheduleQueryService.handle(ArgumentMatchers.any(GetAllScheduleQuery.class)))
                .thenReturn(Collections.singletonList(schedule));

        // Act
        ResponseEntity<?> response = scheduleController.getAllSchedule();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, ((java.util.List<?>) response.getBody()).size());
    }
}