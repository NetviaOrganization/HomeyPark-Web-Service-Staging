package com.homeypark.web_service.parkings.application.internal.outboundservices.acl;

import com.homeypark.web_service.profiles.interfaces.acl.ProfileContextFacade;
import org.springframework.stereotype.Service;

@Service("parkingExternalProfileService")
public class ExternalProfileService {
    private final ProfileContextFacade userContextFacade;

    public ExternalProfileService(ProfileContextFacade userContextFacade) {
        this.userContextFacade = userContextFacade;
    }
    public boolean checkProfileExistById(Long userId) {
        return userContextFacade.checkProfileExistById(userId);
    }
}
