package com.homeypark.web_service.profiles.application.internal.outboundservices.acl;

import com.homeypark.web_service.iam.interfaces.acl.IamContextFacade;
import org.springframework.stereotype.Service;

@Service
public class ExternalUserService {
    private final IamContextFacade iamContextFacade;

    public ExternalUserService(IamContextFacade iamContextFacade) {
        this.iamContextFacade = iamContextFacade;
    }
    public boolean checkUserExistsByUserId(Long UserId){return iamContextFacade.checkProfileExistsByUserId(UserId);}
}
