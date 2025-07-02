package com.homeypark.web_service.parkings.domain.services;

import com.homeypark.web_service.parkings.domain.model.commands.UpdateLocationCommand;
import com.homeypark.web_service.parkings.domain.model.entities.Location;

import java.util.Optional;

public interface LocationCommandService {
    Optional<Location> handle(UpdateLocationCommand command);
}
