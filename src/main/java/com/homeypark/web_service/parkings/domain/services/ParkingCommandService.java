package com.homeypark.web_service.parkings.domain.services;

import com.homeypark.web_service.parkings.domain.model.aggregates.Parking;
import com.homeypark.web_service.parkings.domain.model.commands.CreateParkingCommand;
import com.homeypark.web_service.parkings.domain.model.commands.DeleteParkingCommand;
import com.homeypark.web_service.parkings.domain.model.commands.UpdateParkingCommand;

import java.util.Optional;

public interface ParkingCommandService {
    Optional<Parking> handle(CreateParkingCommand command);
    Optional<Parking> handle(UpdateParkingCommand command);
    void handle(DeleteParkingCommand command);
}
