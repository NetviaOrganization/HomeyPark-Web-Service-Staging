package com.homeypark.web_service.iam.domain.model.commands;

/**
 * VerifyEmailCommand
 * <p>
 *     This command is used to verify a user's email address.
 * </p>
 * @param userId the ID of the user whose email should be verified
 */
public record VerifyEmailCommand(Long userId) {
}
