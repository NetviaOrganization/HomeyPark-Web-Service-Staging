package com.homeypark.web_service.profiles.domain.services;

import com.homeypark.web_service.profiles.domain.model.aggregates.Profile;
import com.homeypark.web_service.profiles.domain.model.commands.CreateProfileCommand;
import com.homeypark.web_service.profiles.domain.model.commands.DeleteProfileCommand;
import com.homeypark.web_service.profiles.domain.model.commands.UpdateProfileCommand;

import java.util.Optional;

public interface ProfileCommandService {
    Optional<Profile> handle(CreateProfileCommand command);
    Optional<Profile> handle(UpdateProfileCommand command);
    void handle(DeleteProfileCommand command);
}