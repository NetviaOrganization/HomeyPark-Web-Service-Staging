package com.homeypark.web_service.vehicles.domain.model.commands;

public record CreateVehicleCommand(String licensePlate, String model, String brand, Long profileId) {
}
