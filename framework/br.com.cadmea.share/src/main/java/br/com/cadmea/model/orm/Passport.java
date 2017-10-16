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
@EqualsAndHashCode(callSuper = false)
@Table(name = "person_passport")
@AttributeOverrides(@AttributeOverride(name = "id", column = @Column(name = "pas_id", nullable = false)))
public class Passport extends BaseEntityPersistent {

    @NotNull
    @Column(nullable = false)
    //define the country where you born
    private Boolean nationality;

    @Column(name = "passport_number", nullable = false, length = 155, unique = true)
    private String number;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Media picture;

    @NotNull
    @OneToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "cou_id", referencedColumnName = "cou_id", nullable = false)
    private Country country;


}
