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
@Table(name = "phone")
@AttributeOverrides(@AttributeOverride(name = "id", column = @Column(name = "pho_id", nullable = false)))
@EqualsAndHashCode(callSuper = false)
public class Phone extends BaseEntityPersistent {

    @NotNull
    @Column(name = "pho_number", nullable = false, length = 15)
    private String number;

    @NotNull
    @Column(name = "is_default", nullable = false)
    private Boolean isDefault;

}
