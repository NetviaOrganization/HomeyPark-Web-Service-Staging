package com.homeypark.web_service.iam.interfaces.rest.transform;

import com.homeypark.web_service.iam.domain.model.commands.SignUpCommand;
import com.homeypark.web_service.iam.domain.model.entities.Role;
import com.homeypark.web_service.iam.interfaces.rest.resources.SignUpResource;

import java.util.ArrayList;

public class SignUpCommandFromResourceAssembler {

  public static SignUpCommand toCommandFromResource(SignUpResource resource) {
    var roles = resource.roles() != null
        ? resource.roles().stream().map(Role::toRoleFromName).toList()
        : new ArrayList<Role>();
    return new SignUpCommand(
            resource.firstName(),
            resource.lastName(),
            resource.birthDate(),
            resource.email(),
            resource.password(),
            roles
    );
  }
}
