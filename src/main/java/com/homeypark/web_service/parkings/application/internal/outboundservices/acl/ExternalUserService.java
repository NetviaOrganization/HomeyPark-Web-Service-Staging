package com.homeypark.web_service.parkings.application.internal.outboundservices.acl;

import com.homeypark.web_service.iam.interfaces.acl.IamContextFacade;
import com.homeypark.web_service.profiles.interfaces.acl.ProfileContextFacade;
import com.homeypark.web_service.shared.interfaces.rest.resources.UserInfoResource;
import org.springframework.stereotype.Service;

@Service("parkingExternalUserService")
public class ExternalUserService {
    private final IamContextFacade iamContextFacade;
    private final ProfileContextFacade profileContextFacade;

    public ExternalUserService(IamContextFacade iamContextFacade, ProfileContextFacade profileContextFacade) {
        this.iamContextFacade = iamContextFacade;
        this.profileContextFacade = profileContextFacade;
    }

    public UserInfoResource getUserInfoByUserId(Long userId) {
        String email = iamContextFacade.fetchEmailByUserId(userId);
        boolean verifiedEmail = iamContextFacade.fetchUserVerifiedStatusByUserId(userId);
        
        return new UserInfoResource(userId, email, verifiedEmail);
    }

    public UserInfoResource getUserInfoByProfileId(Long profileId) {
        var userIdOpt = profileContextFacade.fetchUserIdByProfileId(profileId);
        if (userIdOpt.isEmpty()) {
            return null; // or throw an exception based on your error handling strategy
        }
        
        Long userId = userIdOpt.get();
        String email = iamContextFacade.fetchEmailByUserId(userId);
        boolean verifiedEmail = iamContextFacade.fetchUserVerifiedStatusByUserId(userId);
        
        return new UserInfoResource(userId, email, verifiedEmail);
    }
}
