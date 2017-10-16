package br.com.cadmea.model.orm;

import br.com.cadmea.model.BaseEntityPersistent;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Gilberto Santos
 */
@Entity
@Data
@Table(name = "social_network")
@AttributeOverrides(@AttributeOverride(name = "id", column = @Column(name = "soc_id", nullable = false)))
@EqualsAndHashCode(callSuper = false)
public class SocialNetwork extends BaseEntityPersistent {


    @NotNull
    @Column(name = "id_network", nullable = false)
    private String idNetwork;

    @NotNull
    @Column(name = "primary_contact", nullable = false)
    private String primaryContact;

    @Column(name = "link")
    private String link;

    @Column(name = "picture_profile")
    private String pictureProfile;

    @Column(name = "type")
    private TypeSocial type;

    @NotNull
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY, targetEntity = UserSystem.class)
    private UserSystem userSystem;

    static enum TypeSocial {
        FACEBOOK(1), GOOGLE(2);
        int type = 0;

        TypeSocial(final int i) {
            type = i;
        }
    }


}
