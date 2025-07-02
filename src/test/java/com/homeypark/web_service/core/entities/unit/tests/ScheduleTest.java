package com.homeypark.web_service.core.entities.unit.tests;

import com.homeypark.web_service.parkings.domain.model.commands.CreateScheduleCommand;
import com.homeypark.web_service.parkings.domain.model.commands.UpdateScheduleCommand;
import com.homeypark.web_service.parkings.domain.model.entities.Schedule;
import com.homeypark.web_service.parkings.domain.model.valueobjects.WeekDay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ScheduleTest {

    private CreateScheduleCommand createCommand;
    private UpdateScheduleCommand updateCommand;
    private Schedule mockSchedule;

    @BeforeEach
    void setUp() {
        // Arrange: Initialize commands and mock
        createCommand = new CreateScheduleCommand(1L, "MONDAY",
                LocalTime.of(8, 0),
                LocalTime.of(18, 0));

        updateCommand = new UpdateScheduleCommand(1L, "TUESDAY",
                LocalTime.of(9, 0),
                LocalTime.of(17, 0));

        mockSchedule = mock(Schedule.class);
    }

    @Test
    void testConstructorWithCommand() {
        // Arrange
        CreateScheduleCommand command = createCommand;

        // Act
        Schedule schedule = new Schedule(createCommand);

        // Assert
        assertEquals("MONDAY", schedule.getDay().toString());
        assertEquals(LocalTime.of(8, 0), schedule.getStartTime());
        assertEquals(LocalTime.of(18, 0), schedule.getEndTime());
    }

    @Test
    void testUpdateSchedule() {
        // Arrange
        UpdateScheduleCommand command = updateCommand;

        // Act
        mockSchedule.updatedSchedule(updateCommand);

        // Assert
        verify(mockSchedule).updatedSchedule(updateCommand);
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        LocalTime startTime = LocalTime.of(10, 0);
        LocalTime endTime = LocalTime.of(16, 0);

        // Act
        mockSchedule.setDay(WeekDay.valueOf("WEDNESDAY"));
        mockSchedule.setStartTime(startTime);
        mockSchedule.setEndTime(endTime);

        // Assert
        verify(mockSchedule).setDay(WeekDay.valueOf("WEDNESDAY"));
        verify(mockSchedule).setStartTime(startTime);
        verify(mockSchedule).setEndTime(endTime);
    }
}