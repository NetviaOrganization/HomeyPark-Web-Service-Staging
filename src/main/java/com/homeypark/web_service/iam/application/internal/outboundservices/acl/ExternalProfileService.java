package com.homeypark.web_service.iam.application.internal.outboundservices.acl;

import com.homeypark.web_service.profiles.interfaces.acl.ProfileContextFacade;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service("iamExternalProfileService")
public class ExternalProfileService {
    private final ProfileContextFacade profilesContextFacade;
    public ExternalProfileService(ProfileContextFacade profilesContextFacade) {
        this.profilesContextFacade = profilesContextFacade;
    }

    public Long createProfile(Long userId, String firstName, String lastName, LocalDate birthDate) {
        return profilesContextFacade.createProfile(userId, firstName, lastName, birthDate);
    }
}
