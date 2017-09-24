package br.com.cadmea.model.orm;

import br.com.cadmea.comuns.orm.enums.Gender;
import br.com.cadmea.comuns.orm.enums.Relationship;
import br.com.cadmea.model.BaseEntityPersistent;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Gilberto Santos
 */
@Entity
@Data
@Table(name = "cadmea_person_member")
@AttributeOverrides(@AttributeOverride(name = "id", column = @Column(name = "pes_id", nullable = false)))
@EqualsAndHashCode(callSuper = false)
public class Person extends BaseEntityPersistent {

    // can't have whitespace
    @Column(nullable = false, length = 35, name = "pes_name")
    private String name;

    @Column(nullable = true, length = 250, name = "pes_surname")
    private String surname;

    @NotNull
    @Enumerated
    @Column(nullable = false, length = 1, name = "pes_gender")
    private Gender gender;

    @Enumerated
    @Column(nullable = true, length = 1, name = "pes_relationship")
    private Relationship relationship;

    @NotNull
    @Column(nullable = false, name = "pes_date_of_birth")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfBirth;

    @OneToOne(fetch = FetchType.LAZY)
    private Country country;


}
