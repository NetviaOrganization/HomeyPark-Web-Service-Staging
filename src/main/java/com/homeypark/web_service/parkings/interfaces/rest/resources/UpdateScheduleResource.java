package com.homeypark.web_service.parkings.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalTime;

public record UpdateScheduleResource(
        @Pattern(regexp = "^(MONDAY|TUESDAY|WEDNESDAY|THURSDAY|FRIDAY|SATURDAY|SUNDAY)$",
                message = "{schedule.day.invalid}")
        String day,
        @NotNull(message = "{schedule.startTime.not.null}")
        LocalTime startTime,
        @NotNull(message = "{schedule.endTime.not.null}")
        LocalTime endTime
) {
}
