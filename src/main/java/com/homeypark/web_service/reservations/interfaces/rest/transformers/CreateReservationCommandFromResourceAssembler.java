package com.homeypark.web_service.reservations.interfaces.rest.transformers;

import com.homeypark.web_service.reservations.domain.model.commands.CreateReservationCommand;
import com.homeypark.web_service.reservations.interfaces.rest.resources.CreateReservationResource;


public class CreateReservationCommandFromResourceAssembler {
    public static CreateReservationCommand toCommandFromResource(CreateReservationResource resource) {
        return new CreateReservationCommand(
                resource.hoursRegistered(),
                resource.totalFare(),
                resource.reservationDate(),
                resource.startTime(),
                resource.endTime(),
                resource.guestId(),
                resource.hostId(),
                resource.parkingId(),
                resource.vehicleId()
        );
    }
}
