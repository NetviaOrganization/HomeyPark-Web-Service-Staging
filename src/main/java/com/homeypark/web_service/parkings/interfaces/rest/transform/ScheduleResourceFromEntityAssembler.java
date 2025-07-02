package com.homeypark.web_service.parkings.interfaces.rest.transform;

import com.homeypark.web_service.parkings.domain.model.entities.Schedule;
import com.homeypark.web_service.parkings.interfaces.rest.resources.ScheduleResource;

public class ScheduleResourceFromEntityAssembler {
    public static ScheduleResource toResourceFromEntity(Schedule entity) {
        return new ScheduleResource(
                entity.getId(),
                entity.getParking().getId(),
                entity.getDay().name(),
                entity.getStartTime(),
                entity.getEndTime(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
