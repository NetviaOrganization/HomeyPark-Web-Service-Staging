package com.homeypark.web_service.iam.interfaces.acl;

import com.homeypark.web_service.iam.domain.model.aggregates.User;
import java.util.List;
import java.util.Optional;

/**
 * IamContextFacade
 * <p>
 *     This interface provides the methods to interact with the IAM context.
 *     It provides the methods to create a user, fetch the username by userId.
 *     The implementation of this interface will be provided by the IAM module.
 *     This interface is used by the ACL module to interact with the IAM module.
 * </p>
 */
public interface IamContextFacade {
  /**
   * fetchUserIdByUsername
   * <p>
   *     This method is used to fetch the userId by username.
   * </p>
   * @param email the username of the user
   * @return the user id of the user if found, 0L otherwise
   */
  Long fetchUserIdByEmail(String email);

  /**
   * fetchUsernameByUserId
   * <p>
   *     This method is used to fetch the username by userId.
   * </p>
   * @param userId the userId of the user
   * @return the username of the user if found, empty string otherwise
   */
  String fetchEmailByUserId(Long userId);

  Boolean checkProfileExistsByUserId(Long userId);

  /**
   * fetchUserVerifiedStatusByUserId
   * <p>
   *     This method is used to fetch the user's email verification status by userId.
   * </p>
   * @param userId the userId of the user
   * @return true if the user's email is verified, false otherwise
   */
  Boolean fetchUserVerifiedStatusByUserId(Long userId);

  /**
   * fetchUserDiscountStatusByUserId
   * <p>
   *     This method is used to fetch the user's discount status by userId.
   * </p>
   * @param userId the userId of the user
   * @return true if the user has discount, false otherwise
   */
  Boolean fetchUserDiscountStatusByUserId(Long userId);

  /**
   * activateUserDiscountByUserId
   * <p>
   *     This method is used to activate discount for a user by userId.
   * </p>
   * @param userId the userId of the user
   */
  void activateUserDiscountByUserId(Long userId);

  /**
   * activateUserDiscount
   * <p>
   *     This method is used to activate discount for a user by userId and return the updated user.
   * </p>
   * @param userId the userId of the user
   * @return the updated user entity if found, empty otherwise
   */
  Optional<User> activateUserDiscount(Long userId);

  /**
   * getUserHasDiscount
   * <p>
   *     This method is used to check if a user has discount activated.
   * </p>
   * @param userId the userId of the user
   * @return true if the user has discount, false otherwise
   */
  Boolean getUserHasDiscount(Long userId);
}
