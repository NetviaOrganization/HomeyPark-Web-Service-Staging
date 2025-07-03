package com.homeypark.web_service.iam.interfaces.rest;

import com.homeypark.web_service.iam.domain.model.queries.GetAllUsersQuery;
import com.homeypark.web_service.iam.domain.model.queries.GetUserByIdQuery;
import com.homeypark.web_service.iam.domain.services.UserCommandService;
import com.homeypark.web_service.iam.domain.services.UserQueryService;
import com.homeypark.web_service.iam.interfaces.rest.resources.UserResource;
import com.homeypark.web_service.iam.interfaces.rest.resources.VerifyEmailResource;
import com.homeypark.web_service.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import com.homeypark.web_service.iam.interfaces.rest.transform.VerifyEmailCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class is a REST controller that exposes the users resource.
 * It includes the following operations:
 * - GET /api/v1/users: returns all the users
 * - GET /api/v1/users/{userId}: returns the user with the given id
 * - PUT /api/v1/users/verify-email: verifies a user's email address
 **/
@RestController
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Users", description = "User Management Endpoints")
public class UsersController {

  private final UserQueryService userQueryService;
  private final UserCommandService userCommandService;

  public UsersController(UserQueryService userQueryService, UserCommandService userCommandService) {
    this.userQueryService = userQueryService;
    this.userCommandService = userCommandService;
  }

  /**
   * This method returns all the users.
   *
   * @return a list of user resources.
   * @see UserResource
   */
  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<List<UserResource>> getAllUsers() {
    var getAllUsersQuery = new GetAllUsersQuery();
    var users = userQueryService.handle(getAllUsersQuery);
    var userResources = users.stream()
        .map(UserResourceFromEntityAssembler::toResourceFromEntity)
        .toList();
    return ResponseEntity.ok(userResources);
  }

  /**
   * This method returns the user with the given id.
   *
   * @param userId the user id.
   * @return the user resource with the given id
   * @throws RuntimeException if the user is not found
   * @see UserResource
   */
  @GetMapping(value = "/{userId}")
  public ResponseEntity<UserResource> getUserById(@PathVariable Long userId) {
    var getUserByIdQuery = new GetUserByIdQuery(userId);
    var user = userQueryService.handle(getUserByIdQuery);
    if (user.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
    return ResponseEntity.ok(userResource);
  }

  /**
   * This method verifies a user's email address.
   *
   * @param verifyEmailResource the verify email request body.
   * @return the updated user resource with verified email
   * @see UserResource
   * @see VerifyEmailResource
   */
  @PutMapping(value = "/verify-email")
  public ResponseEntity<UserResource> verifyEmail(@Valid @RequestBody VerifyEmailResource verifyEmailResource) {
    var verifyEmailCommand = VerifyEmailCommandFromResourceAssembler
        .toCommandFromResource(verifyEmailResource);
    var user = userCommandService.handle(verifyEmailCommand);
    if (user.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
    return ResponseEntity.ok(userResource);
  }
}
