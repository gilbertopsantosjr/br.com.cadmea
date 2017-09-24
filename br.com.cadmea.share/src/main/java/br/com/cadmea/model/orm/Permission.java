package br.com.cadmea.model.orm;

import br.com.cadmea.model.BaseEntityPersistent;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * @author Gilberto Santos
 */
@Entity
@Data
@Table(name = "cadmea_user_permission")
@AttributeOverrides(@AttributeOverride(name = "id",
        column = @Column(name = "per_id", nullable = false)))
@NamedQuery(name = "Permission.findRolesByUser", query = "SELECT p.id, p.role FROM Permission as p INNER JOIN p.users as u WHERE u.id = :usu_id ")
@EqualsAndHashCode(callSuper = false)
public class Permission extends BaseEntityPersistent {

    @NotNull
    @Size(max = 45)
    @Column(name = "role", length = 45, nullable = false, unique = true)
    private String role;

    @ManyToMany(targetEntity = UserSystem.class, mappedBy = "permissions")
    @JsonIgnore
    private Set<UserSystem> users;


}
