/**
 *
 */
package br.com.cadmea.web.dao;

import br.com.cadmea.comuns.exceptions.SystemException;
import br.com.cadmea.model.dao.DaoGenericoImp;
import br.com.cadmea.model.orm.Role;
import br.com.cadmea.model.orm.UserSystem;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Gilberto Santos
 */
@Repository
public class RoleDaoImp extends DaoGenericoImp<Role> implements RoleDao {


    /**
     * this method gets all {@link Role} by User
     *
     * @param {@link UserSystem} user
     * @return a List<Role>
     * @throws SystemException
     */
    @Override
    public List<Role> getPermissionsOf(final UserSystem user) {
        if (user == null) {
            throw new SystemException("User is required for this operation");
        }

        final List<Role> list = new ArrayList<>();
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("usu_id", user.getId());

        findByNamedQuery("findRolesByUser", parameters).forEach(a -> {
            list.add(a);
        });
        return list;
    }


}
