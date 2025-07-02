package com.homeypark.web_service.vehicles.domain.model.commands;

public record UpdateVehicleCommand(Long vehicleId, String licensePlate, String model, String brand) {
}
