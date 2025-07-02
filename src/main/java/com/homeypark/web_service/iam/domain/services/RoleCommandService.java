package com.homeypark.web_service.iam.domain.services;

import com.homeypark.web_service.iam.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {
  void handle(SeedRolesCommand command);
}
