package com.homeypark.web_service.shared.interfaces.rest.resources;

/**
 * UserInfoResource
 * <p>
 *     This resource represents basic user information including email verification status.
 * </p>
 */
public record UserInfoResource(
        Long userId,
        String email,
        boolean verifiedEmail
) {
}
