package com.homeypark.web_service.profiles.interfaces.acl;

import java.time.LocalDate;

public interface ProfileContextFacade {
    boolean checkProfileExistById(Long userId);
    Long createProfile(Long userId, String firstName, String lastName, LocalDate birthDate);
}
