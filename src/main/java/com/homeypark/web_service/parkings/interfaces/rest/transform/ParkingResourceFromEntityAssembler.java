package com.homeypark.web_service.parkings.interfaces.rest.transform;

import com.homeypark.web_service.parkings.domain.model.aggregates.Parking;
import com.homeypark.web_service.parkings.interfaces.rest.resources.ParkingResource;

public class ParkingResourceFromEntityAssembler {
    public static ParkingResource toResourceFromEntity(Parking entity){
        return new ParkingResource(
                entity.getId(),
                entity.getProfileId().profileIdAsPrimitive(),
                entity.getWidth(),
                entity.getLength(),
                entity.getHeight(),
                entity.getPrice(),
                entity.getPhone(),
                entity.getSpace(),
                entity.getDescription(),
                LocationResourceFromEntityAssembler.toResourceFromEntity(entity.getLocation()),
                entity.getSchedules().stream().map(ScheduleResourceFromEntityAssembler::toResourceFromEntity).toList(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
