package com.homeypark.web_service.iam.interfaces.rest.transform;

import com.homeypark.web_service.iam.domain.model.commands.SignInCommand;
import com.homeypark.web_service.iam.interfaces.rest.resources.SignInResource;

public class SignInCommandFromResourceAssembler {

  public static SignInCommand toCommandFromResource(SignInResource signInResource) {
    return new SignInCommand(signInResource.email(), signInResource.password());
  }
}
