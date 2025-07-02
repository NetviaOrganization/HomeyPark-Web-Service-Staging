package com.homeypark.web_service.profiles.interfaces.rest.transformers;

import com.homeypark.web_service.profiles.domain.model.commands.UpdateProfileCommand;
import com.homeypark.web_service.profiles.interfaces.rest.resources.UpdateProfileResource;

public class UpdateProfileCommandFromResource {
    public static UpdateProfileCommand toCommandFromResource(Long userId, UpdateProfileResource resource) {
        return new UpdateProfileCommand(userId, resource.firstName(), resource.lastName(), resource.birthDate());
    }
}
