package com.homeypark.web_service.parkings.domain.services;

import com.homeypark.web_service.parkings.domain.model.commands.CreateScheduleCommand;
import com.homeypark.web_service.parkings.domain.model.commands.UpdateScheduleCommand;
import com.homeypark.web_service.parkings.domain.model.entities.Schedule;

import java.util.Optional;

public interface ScheduleCommandService {
    Optional<Schedule> handle(CreateScheduleCommand command);
    Optional<Schedule> handle(UpdateScheduleCommand command);
}
