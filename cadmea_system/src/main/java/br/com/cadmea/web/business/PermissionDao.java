package br.com.cadmea.web.business;

import br.com.cadmea.model.dao.DaoGenerico;
import br.com.cadmea.model.orm.Permission;
import br.com.cadmea.model.orm.UserSystem;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface PermissionDao extends DaoGenerico<Permission> {

    public List<Permission> getPermissionsOf(final UserSystem user);
}
