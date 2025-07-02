package com.homeypark.web_service.iam.application.acl;

import com.homeypark.web_service.iam.domain.model.queries.GetUserByIdQuery;
import com.homeypark.web_service.iam.domain.model.queries.GetUserByEmailQuery;
import com.homeypark.web_service.iam.domain.services.UserCommandService;
import com.homeypark.web_service.iam.domain.services.UserQueryService;
import com.homeypark.web_service.iam.interfaces.acl.IamContextFacade;
import io.jsonwebtoken.lang.Strings;
import org.springframework.stereotype.Service;

/**
 * IamContextFacadeImpl
 * <p>
 *     This class provides the implementation of the IamContextFacade interface.
 *     This class is used by the ACL module to interact with the IAM module.
 * </p>
 */
@Service
public class IamContextFacadeImpl implements IamContextFacade {
    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    /**
     * Constructor
     * @param userCommandService the {@link UserCommandService} user command service
     * @param userQueryService the {@link UserQueryService} user query service
     */
    public IamContextFacadeImpl(UserCommandService userCommandService, UserQueryService userQueryService) {
        this.userCommandService = userCommandService;
        this.userQueryService = userQueryService;
    }

    // inherited javadoc
    @Override
    public Long fetchUserIdByEmail(String email) {
        var getUserByEmailQuery = new GetUserByEmailQuery(email);
        var result = userQueryService.handle(getUserByEmailQuery);
        if (result.isEmpty()) return 0L;
        return result.get().getId();
    }

    @Override
    public String fetchEmailByUserId(Long userId) {
        var getUserByIdQuery = new GetUserByIdQuery(userId);
        var result = userQueryService.handle(getUserByIdQuery);
        if (result.isEmpty()) return Strings.EMPTY;
        return result.get().getEmail();
    }

    @Override
    public Boolean checkProfileExistsByUserId(Long userId) {
        var getUserByIdQuery = new  GetUserByIdQuery(userId);
        var result = userQueryService.handle(getUserByIdQuery);
        return result.isPresent();
    }
}
