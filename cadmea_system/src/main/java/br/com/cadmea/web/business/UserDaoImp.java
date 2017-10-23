package br.com.cadmea.web.business;

import br.com.cadmea.model.dao.DaoGenericoImp;
import br.com.cadmea.model.orm.Person;
import br.com.cadmea.model.orm.UserSystem;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author Gilberto Santos
 */
@Repository
class UserDaoImp extends DaoGenericoImp<UserSystem, Long> implements UserDao {

    @Inject
    private PermissionDao permissionDao;

    @Override
    public List<UserSystem> factoryEntity(final List<UserSystem> list) {
        final List<UserSystem> result = new ArrayList<>();
        for (final Object o : list) {
            final Object[] cols = (Object[]) o;

            final UserSystem tmp = new UserSystem();
            tmp.setId((Long) cols[0]);
            tmp.setEmail((String) cols[1]);
            tmp.setNickname((String) cols[2]);

            final Person p = new Person();
            p.setName((String) cols[3]);

            tmp.setPerson(p);

            tmp.setPermissions(new HashSet<>());

            permissionDao.getPermissionsOf(tmp).forEach(permission -> {
                tmp.getPermissions().add(permission);
            });

            result.add(tmp);
        }
        return result;
    }


}
