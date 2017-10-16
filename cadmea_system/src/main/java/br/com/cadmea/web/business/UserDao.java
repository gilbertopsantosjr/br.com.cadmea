package br.com.cadmea.web.business;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import br.com.cadmea.model.dao.DaoGenericoImp;
import br.com.cadmea.model.orm.Person;
import br.com.cadmea.model.orm.UserSystem;

/**
 * @author Gilberto Santos
 */
@Repository
class UserDao extends DaoGenericoImp<UserSystem, Long> {
	
	@Inject
	private PermissionDao permissionDao;

	@Override
	public List<UserSystem> factoryEntity(List<UserSystem> list) {
		List<UserSystem> result = new ArrayList<>();
		for (Object o : list) {
		    Object[] cols = (Object[]) o;
		    
		    UserSystem tmp = new UserSystem();
		    tmp.setId((Long) cols[0]);
		    tmp.setEmail( (String) cols[1]); 
		    tmp.setNickname( (String) cols[2] );
		    
		    Person p = new Person();
		    p.setName((String) cols[3]);
		    
		    tmp.setPerson(p);
		    
		    tmp.setPermissions(new HashSet<>());
		    
		    permissionDao.getPermissionsOf(tmp).forEach( permission -> {
		    	tmp.getPermissions().add(permission);
		    });
		    
		    result.add(tmp);
		}
		return result;
	}

	
	
}
