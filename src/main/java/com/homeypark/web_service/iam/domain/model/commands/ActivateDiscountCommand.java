package com.homeypark.web_service.iam.domain.model.commands;

public record ActivateDiscountCommand(Long userId) {
    public ActivateDiscountCommand {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User id cannot be null or less than or equal to zero");
        }
    }
}
