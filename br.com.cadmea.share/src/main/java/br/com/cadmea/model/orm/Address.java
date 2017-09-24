/**
 *
 */
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
@Table(name = "cadmea_address")
@AttributeOverrides(@AttributeOverride(name = "id",
        column = @Column(name = "end_id", nullable = false)))
public class Address extends BaseEntityPersistent {

    @NotNull
    @OneToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "cid_id", referencedColumnName = "cid_id",
            nullable = false)
    private City city;

    @NotNull
    private String floor;

    private String geolocation;

    @NotNull
    private String address;

    @NotNull
    private String zip;

    @NotNull
    private String complement;


}
