package com.homeypark.web_service.iam.interfaces.rest.transform;

import com.homeypark.web_service.iam.domain.model.commands.VerifyEmailCommand;
import com.homeypark.web_service.iam.interfaces.rest.resources.VerifyEmailResource;

/**
 * VerifyEmailCommandFromResourceAssembler
 * <p>
 *     This class is responsible for assembling a {@link VerifyEmailCommand} from a {@link VerifyEmailResource}.
 * </p>
 */
public class VerifyEmailCommandFromResourceAssembler {

    /**
     * Assemble a {@link VerifyEmailCommand} from a {@link VerifyEmailResource}.
     * @param resource the {@link VerifyEmailResource} resource.
     * @return the {@link VerifyEmailCommand} command.
     */
    public static VerifyEmailCommand toCommandFromResource(VerifyEmailResource resource) {
        return new VerifyEmailCommand(resource.userId());
    }
}
