/**
 *
 */
package br.com.cadmea.web.dao;

import br.com.cadmea.comuns.exceptions.SystemException;
import br.com.cadmea.model.dao.DaoGenericoImp;
import br.com.cadmea.model.orm.Permission;
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
public class PermissionDaoImp extends DaoGenericoImp<Permission> implements PermissionDao {


    /**
     * this method gets all {@link Permission} by User
     *
     * @param user {@link UserSystem}
     * @return a List<Permission>
     * @throws SystemException
     */
    @Override
    public List<Permission> getPermissionsOf(final UserSystem user) {
        if (user == null) {
            throw new SystemException("User is required for this operation");
        }

        final List<Permission> list = new ArrayList<>();
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("usu_id", user.getId());

        findByNamedQuery("findRolesByUser", parameters).forEach(a -> {
            list.add(a);
        });
        return list;
    }

    /**
     *
     */
    @Override
    public List<Permission> factoryEntity(final List<Permission> list) {
        final List<Permission> returnedList = new ArrayList<>();
        for (final Object o : list) {
            final Object[] cols = (Object[]) o;
            final Permission p = new Permission();
            p.setId((Long) cols[0]);
            p.setRole((String) cols[1]);
            returnedList.add(p);
        }
        return returnedList;
    }

}
