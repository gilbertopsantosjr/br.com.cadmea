package br.com.cadmea.model.orm;

import br.com.cadmea.comuns.orm.enums.Situation;
import br.com.cadmea.model.BaseEntityPersistent;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Gilberto Santos
 */
@Entity
@Data
@Table(name = "cadmea_user_permission")
@AttributeOverrides(@AttributeOverride(name = "id", column = @Column(name = "per_id", nullable = false)))
@EqualsAndHashCode(callSuper = false)
@NamedQuery(name = "Role.findRolesByUser", query = "SELECT p FROM Role as p INNER JOIN p.cadmeaSystem as c INNER JOIN c.users as u WHERE u.id = :usu_id ")
public class Role extends BaseEntityPersistent {

    @NotNull
    @Size(max = 45)
    @Column(name = "role_name", length = 45, nullable = false)
    private String name;
    // a system can't have two roles with the same name

    @NotNull
    @Enumerated
    @Column(nullable = false, length = 1, name = "situation")
    private Situation situation;

    @NotNull
    @OneToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "sys_id", referencedColumnName = "sys_id", nullable = false)
    private CadmeaSystem cadmeaSystem;



}
