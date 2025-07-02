package com.homeypark.web_service.vehicles.domain.services;

import com.homeypark.web_service.vehicles.domain.model.commands.CreateVehicleCommand;
import com.homeypark.web_service.vehicles.domain.model.commands.DeleteVehicleCommand;
import com.homeypark.web_service.vehicles.domain.model.commands.UpdateVehicleCommand;
import com.homeypark.web_service.vehicles.domain.model.aggregates.Vehicle;

import java.util.Optional;

public interface VehicleCommandService {
    Optional<Vehicle> handle(CreateVehicleCommand command);
    Optional<Vehicle> handle(UpdateVehicleCommand command);
    void handle(DeleteVehicleCommand command);
}