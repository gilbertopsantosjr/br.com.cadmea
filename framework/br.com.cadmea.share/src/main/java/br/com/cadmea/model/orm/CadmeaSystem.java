package br.com.cadmea.model.orm;

import br.com.cadmea.model.BaseEntityPersistent;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * authorized a user of system
 *
 * @author Gilberto Santos
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "cadmea_system")
@AttributeOverrides(@AttributeOverride(name = "id", column = @Column(name = "sys_id", nullable = false)))
public class CadmeaSystem extends BaseEntityPersistent {

    /**
     * these values should be get from a rest service in gilbertosantos.com
     */

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "sys_identity", nullable = false, length = 150, unique = true)
    private String identity;

    @NotNull
    @Size(min = 3, max = 150)
    @Column(name = "sys_name", nullable = false, length = 150)
    private String name;

    @Column(name = "sys_description")
    private String description;

    /* this would be the url the login system should call after validate the login */
    @Column(name = "sys_url", nullable = false, length = 150)
    private String ulr;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = UserSystem.class, mappedBy = "systems")
    private List<UserSystem> users;


}
