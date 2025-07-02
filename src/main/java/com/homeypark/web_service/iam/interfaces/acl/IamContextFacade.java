package com.homeypark.web_service.iam.interfaces.acl;

import java.util.List;

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
}
