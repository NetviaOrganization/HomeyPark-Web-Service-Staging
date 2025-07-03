package com.homeypark.web_service.profiles.interfaces.rest.transformers;

import com.homeypark.web_service.profiles.application.internal.outboundservices.acl.ExternalUserService;
import com.homeypark.web_service.profiles.domain.model.aggregates.Profile;
import com.homeypark.web_service.profiles.interfaces.rest.resources.ProfileResource;
import org.springframework.stereotype.Component;

@Component
public class ProfileResourceFromEntityAssembler {
    
    private final ExternalUserService externalUserService;
    
    public ProfileResourceFromEntityAssembler(ExternalUserService externalUserService) {
        this.externalUserService = externalUserService;
    }
    
    public ProfileResource toResourceFromEntity(Profile entity) {
        boolean verifiedEmail = externalUserService.getUserVerifiedStatusByUserId(entity.getUserId().userIdAsPrimitive());
        
        return new ProfileResource(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getBirthDate(),
                entity.getUserId().userIdAsPrimitive(),
                verifiedEmail,
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
