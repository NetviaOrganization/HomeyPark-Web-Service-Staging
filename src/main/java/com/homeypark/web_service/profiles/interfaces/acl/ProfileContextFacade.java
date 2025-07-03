package com.homeypark.web_service.profiles.interfaces.acl;

import java.time.LocalDate;
import java.util.Optional;

public interface ProfileContextFacade {
    boolean checkProfileExistById(Long userId);
    Long createProfile(Long userId, String firstName, String lastName, LocalDate birthDate);
    Optional<Long> fetchUserIdByProfileId(Long profileId);
}
