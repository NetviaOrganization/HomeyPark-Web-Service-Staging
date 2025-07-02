package com.homeypark.web_service.parkings.domain.model.commands;

import com.homeypark.web_service.parkings.domain.model.valueobjects.ProfileId;

public record CreateParkingCommand(
        Long profileId,
        Double width,
        Double length,
        Double height,
        Double price,
        String phone,
        Integer space,
        String description,
        CreateLocationCommand location
) {
}