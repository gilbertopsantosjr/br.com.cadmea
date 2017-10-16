/**
 *
 */
package br.com.cadmea.model.orm;

import br.com.cadmea.model.BaseEntityPersistent;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Gilberto Santos
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "password_recovery")
@AttributeOverrides(@AttributeOverride(name = "id", column = @Column(name = "pas_id", nullable = false)))
public class PasswordResetToken extends BaseEntityPersistent {

    private static final int EXPIRATION = 60 * 24;

    @Column(name = "pas_token", nullable = false, length = 255, unique = true)
    private String token;

    @OneToOne(targetEntity = UserSystem.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private UserSystem user;

    private Date expiryDate;

    public Date getExpiryDate() {
        final long newDate = expiryDate.getTime() * EXPIRATION;
        final Date newExpiryDate = new Date(newDate);
        return newExpiryDate;
    }

}
