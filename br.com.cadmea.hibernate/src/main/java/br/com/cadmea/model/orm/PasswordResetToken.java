/**
 *
 */
package br.com.cadmea.model.orm;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.cadmea.model.BaseEntityPersistent;

/**
 * @author Gilberto Santos
 *
 */
@Entity
@Table(name = "password_recovery")
@AttributeOverrides(@AttributeOverride(name = "id",
    column = @Column(name = "pas_id", nullable = false)))
public class PasswordResetToken extends BaseEntityPersistent {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  private static final int EXPIRATION = 60 * 24;

  private String token;

  @OneToOne(targetEntity = UserSystem.class, fetch = FetchType.EAGER)
  @JoinColumn(nullable = false, name = "user_id")
  private UserSystem user;

  private Date expiryDate;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public UserSystem getUser() {
    return user;
  }

  public void setUser(UserSystem user) {
    this.user = user;
  }

  public Date getExpiryDate() {
    long newDate = expiryDate.getTime() * EXPIRATION;
    Date newExpiryDate = new Date(newDate);
    return newExpiryDate;
  }

  public void setExpiryDate(Date expiryDate) {
    this.expiryDate = expiryDate;
  }

}
