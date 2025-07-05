package com.homeypark.web_service.iam.domain.model.aggregates;

import com.homeypark.web_service.iam.domain.model.entities.Role;
import com.homeypark.web_service.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User aggregate root
 * This class represents the aggregate root for the User entity.
 *
 * @see AuditableAbstractAggregateRoot
 */
@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends AuditableAbstractAggregateRoot<User> {

  @NotBlank
  @Email
  @Column(unique = true)
  private String email;

  @NotBlank
  @Size(max = 120)
  private String password;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
  @JoinTable(name = "user_roles",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles;

  @Column(name = "verified_email")
  private boolean verifiedEmail;

  @Column(name = "has_discount")
  private boolean hasDiscount;

  public User() {
    this.roles = new HashSet<>();
    this.verifiedEmail = false;
    this.hasDiscount = false;
  }
  public User(String email, String password) {
    this.email = email;
    this.password = password;
    this.roles = new HashSet<>();
    this.verifiedEmail = false;
    this.hasDiscount = false;
  }

  public User(String email, String password, List<Role> roles) {
    this(email, password);
    addRoles(roles);
  }

  /**
   * Add a role to the user
   * @param role the role to add
   * @return the user with the added role
   */
  public User addRole(Role role) {
    this.roles.add(role);
    return this;
  }

  /**
   * Add a list of roles to the user
   *
   * @param roles the list of roles to add
   */
  public void addRoles(List<Role> roles) {
    var validatedRoleSet = Role.validateRoleSet(roles);
    this.roles.addAll(validatedRoleSet);
  }

  /**
   * Verify the user's email
   */
  public void verifyEmail() {
    this.verifiedEmail = true;
  }

  /**
   * Check if the user's email is verified
   * @return true if email is verified, false otherwise
   */
  public boolean isEmailVerified() {
    return this.verifiedEmail;
  }

  /**
   * Activate discount for the user
   */
  public void activateDiscount() {
    this.hasDiscount = true;
  }

  /**
   * Check if the user has discount
   * @return true if user has discount, false otherwise
   */
  public boolean hasDiscount() {
    return this.hasDiscount;
  }
}