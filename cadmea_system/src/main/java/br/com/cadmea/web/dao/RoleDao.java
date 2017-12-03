package br.com.cadmea.web.dao;

import br.com.cadmea.model.dao.DaoGenerico;
import br.com.cadmea.model.orm.Role;
import br.com.cadmea.model.orm.UserSystem;

import java.util.List;

public interface RoleDao extends DaoGenerico<Role> {

    List<Role> getPermissionsOf(final UserSystem user);
}
