package com.homeypark.web_service.iam.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * VerifyEmailResource
 * <p>
 *     This resource is used to represent the verify email request.
 * </p>
 * @param userId the ID of the user whose email should be verified
 */
public record VerifyEmailResource(
        @NotNull(message = "User ID is required")
        @Positive(message = "User ID must be positive")
        Long userId
) {
}
