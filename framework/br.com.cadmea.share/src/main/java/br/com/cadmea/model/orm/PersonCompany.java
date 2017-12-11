package br.com.cadmea.model.orm;

import br.com.cadmea.model.BaseEntityPersistent;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "person_company")
@AttributeOverrides(@AttributeOverride(name = "id", column = @Column(name = "pej_id", nullable = false)))
@EqualsAndHashCode(callSuper = false)
public class PersonCompany extends BaseEntityPersistent {

    @Column(nullable = false, length = 16, name = "pej_register")
    private String register;

    @NotNull
    @OneToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "pes_id", referencedColumnName = "pes_id",
            nullable = false, unique = true)
    private Person person;

}
