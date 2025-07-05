package com.homeypark.web_service.iam.domain.services;

import com.homeypark.web_service.iam.domain.model.aggregates.User;
import com.homeypark.web_service.iam.domain.model.commands.ActivateDiscountCommand;
import com.homeypark.web_service.iam.domain.model.commands.SignInCommand;
import com.homeypark.web_service.iam.domain.model.commands.SignUpCommand;
import com.homeypark.web_service.iam.domain.model.commands.VerifyEmailCommand;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Optional;

public interface UserCommandService {
  Optional<ImmutablePair<User, String>> handle(SignInCommand command);
  Optional<User> handle(SignUpCommand command);
  Optional<User> handle(VerifyEmailCommand command);
  Optional<User> handle(ActivateDiscountCommand command);
}
