/**
 * 
 */
package br.com.cadmea.web.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import br.com.cadmea.comuns.exceptions.SystemException;
import br.com.cadmea.model.dao.DaoGenericoImp;
import br.com.cadmea.model.orm.Permission;
import br.com.cadmea.model.orm.UserSystem;

/**
 * @author Gilberto Santos
 *
 */
@Repository
class PermissionDao extends DaoGenericoImp<Permission, Long>  {

	
	/**
	 * this method gets all {@link Permission} by User 
	 * @param user {@link UserSystem}
	 * @return a List<Permission>
	 * @throws SystemException
	 */
	public List<Permission> getPermissionsOf(UserSystem user){
		if(user == null)
			throw new SystemException("User is required for this operation");
		
		final List<Permission> list = new ArrayList<>();
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("usu_id", user.getId());
		
		findByNamedQuery("findRolesByUser", parameters).forEach( a -> {
			list.add(a);
		});
		return list;
	}
	
	/**
	 * 
	 */
	public List<Permission> factoryEntity(List<Permission> list) {
		final List<Permission> returnedList = new ArrayList<>();
		for (Object o : list) {
		    Object[] cols = (Object[]) o;
		    Permission p = new Permission();
		    p.setId((Long) cols[0]);
		    p.setRole((String) cols[1]);
		    returnedList.add(p);
	    }
		return returnedList;
	}
	
}
