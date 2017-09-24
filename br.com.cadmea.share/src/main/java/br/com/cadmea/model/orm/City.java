/**
 *
 */
package br.com.cadmea.model.orm;

import br.com.cadmea.model.BaseEntityPersistent;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * @author Gilberto Santos
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "cadmea_city")
@AttributeOverrides(@AttributeOverride(name = "id",
        column = @Column(name = "cid_id", nullable = false)))
public class City extends BaseEntityPersistent {

    @Column(name = "cit_name", nullable = false, length = 255)
    private String nome;

    @OneToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "est_id", referencedColumnName = "est_id",
            nullable = false)
    private County county;


}
