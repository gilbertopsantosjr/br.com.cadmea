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
@Table(name = "county")
@AttributeOverrides(@AttributeOverride(name = "id", column = @Column(name = "est_id", nullable = false)))
public class County extends BaseEntityPersistent {

    @Column(name = "cou_name", nullable = false, length = 155, unique = true)
    private String name;

    @Column(name = "region", nullable = false, length = 5, unique = true)
    private String region;

    @OneToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "cou_id", referencedColumnName = "cou_id", nullable = false)
    private Country country;

}
