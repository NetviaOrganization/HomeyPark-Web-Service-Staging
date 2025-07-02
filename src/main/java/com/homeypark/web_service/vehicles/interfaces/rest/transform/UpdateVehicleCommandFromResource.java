package com.homeypark.web_service.vehicles.interfaces.rest.transform;

import com.homeypark.web_service.vehicles.domain.model.commands.UpdateVehicleCommand;
import com.homeypark.web_service.vehicles.interfaces.rest.resources.UpdateVehicleResource;

public class UpdateVehicleCommandFromResource {
    public static UpdateVehicleCommand toCommandFromResource(Long vehicleId, UpdateVehicleResource resource) {
        return new UpdateVehicleCommand(vehicleId, resource.licensePlate(), resource.model(), resource.brand());
    }
}
