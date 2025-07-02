package com.homeypark.web_service.parkings.interfaces.rest.transform;

import com.homeypark.web_service.parkings.domain.model.commands.CreateScheduleCommand;
import com.homeypark.web_service.parkings.interfaces.rest.resources.CreateScheduleResource;

public class CreateScheduleCommandFromResourceAssembler {
    public static CreateScheduleCommand toCommandFromResource(CreateScheduleResource resource) {
        return new CreateScheduleCommand(
                resource.parkingId(),
                resource.day(),
                resource.startTime(),
                resource.endTime());
    }
}
