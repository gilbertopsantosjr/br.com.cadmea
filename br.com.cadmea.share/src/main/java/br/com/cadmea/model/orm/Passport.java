package br.com.cadmea.model.orm;

import br.com.cadmea.model.BaseEntityPersistent;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "cadmea_person_passport")
@AttributeOverrides(@AttributeOverride(name = "id",
        column = @Column(name = "pas_id", nullable = false)))
public class Passport extends BaseEntityPersistent {

    @Column(name = "pass_name", nullable = false, length = 155, unique = true)
    private String numero;

    @OneToOne
    private Media picture;

}
