/**
 *
 */
package br.com.cadmea.web.business;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import br.com.cadmea.baseservico.BaseMaintenanceSrvImpl;
import br.com.cadmea.comuns.orm.enums.Result;
import br.com.cadmea.model.orm.PasswordResetToken;
import br.com.cadmea.model.orm.UserSystem;

/**
 * @author Gilberto Santos
 */
@Service
public class UserSrv extends BaseMaintenanceSrvImpl<UserSystem, UserBo> {

  @Inject
  private UserBo userBo;

  @Inject
  private PasswordResetTokenBo passwordResetTokenBo;

  @Override
  protected UserBo getBo() {
    return userBo;
  }

  /**
   * try to get a {@link User} of system by params
   *
   * @param email
   * @param password
   * @return {@link User} if found
   */
  public UserSystem getUserBy(final String email) {
    if (email == null || email.isEmpty())
      throw new RuntimeException("Email can't be null or empty");

    Map<String, Object> params = new HashMap<>();
    params.put("email", email);

    return getBo().find(params, Result.UNIQUE);
  }

  /**
   * create a PasswordResetToken
   *
   * @param user
   * @param token
   */
  public void createPasswordResetTokenForUser(UserSystem user, String token) {
    PasswordResetToken passwordResetToken = new PasswordResetToken();
    passwordResetToken.setToken(token);
    passwordResetToken.setUser(user);
    passwordResetToken.setExpiryDate(new Date());
    passwordResetTokenBo.save(passwordResetToken);
  }

  /**
   *
   * @param token
   * @return {@link PasswordResetToken}
   */
  public PasswordResetToken getPasswordResetToken(String token) {
    return passwordResetTokenBo.getByToken(token);
  }

  /**
   * change the password of user
   * 
   * @param user
   * @param password
   */
  public void changeUserPassword(UserSystem user, String password) {
    user.setPassword(password);
    getBo().save(user);
  }

}
