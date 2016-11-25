package br.com.cadmea.web.rest;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.cadmea.spring.rest.GenericRestService;
import br.com.cadmea.spring.rest.ServicePath;
import br.com.cadmea.spring.rest.exceptions.PreconditionRequiredException;
import br.com.cadmea.spring.rest.exceptions.RestException;
import br.com.cadmea.spring.security.orm.UserAccess;
import br.com.cadmea.web.business.UserSrv;
import br.com.cadmea.web.dto.UserDTo;
import br.com.cadmea.web.model.User;

/**
 * @author Gilberto Santos
 *
 */
@RestController
@RequestMapping(path = ServicePath.PUBLIC_ROOT_PATH + "/user")
public class UserRestSrv extends GenericRestService<User, UserDTo> {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Inject
	private UserSrv userSrv;

	private UserDTo userDTo;

	@Override
	protected void beforeLoadClass() {
		userDTo = new UserDTo();
	}

	@Override
	protected void beforeSave() {
		final String hashPassword = passwordEncoder.encode(getViewForm().getEntity().getPassword());
		getViewForm().getEntity().setPassword(hashPassword);
	}

	@RequestMapping(value = "/authentication/", method = RequestMethod.POST)
	public ResponseEntity<UserDTo> logIn(@RequestBody UserDTo formDto) {
		
		isValidRequest(formDto);
		
		UserDTo found = new UserDTo();
		try {
			final String username = formDto.getEntity().getUsername();
			final String password = passwordEncoder.encode(formDto.getEntity().getPassword());

			final User user = getService().getUserBy(username, password);
			found.setEntity(user);
			
			UserAccess userAccess = new UserAccess(user.getPerson().getName());
			user.getPermissions().forEach( a -> {
				userAccess.getRoles().add(a.getRole());
			});
			
			Authentication auth = new UsernamePasswordAuthenticationToken(userAccess, null, userAccess.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(auth);
			
		} catch (Exception e) {
			throw new RestException(e);
		}

		return new ResponseEntity<UserDTo>(found, HttpStatus.OK);
	}
	
	private void isValidRequest(UserDTo formDto){
		if (!isValid(formDto) && !isValid(formDto.getEntity() ))
			throw new PreconditionRequiredException("Invalid Object !");
		
		if(!isValid(formDto.getEntity().getUsername()) && !isValid( formDto.getEntity().getUsername() ) )
			throw new PreconditionRequiredException("Username is required !");
		
		if(!isValid(formDto.getEntity().getPassword()) && !isValid( formDto.getEntity().getPassword() ) )
			throw new PreconditionRequiredException("Password is required !");
	}
	
	 static boolean isValid(final String str){
		return str != null && !str.isEmpty();
	}
	
	 static boolean isValid(final Object obj){
		return obj != null;
	}

	@Override
	public UserSrv getService() {
		return userSrv;
	}

	@Override
	public UserDTo getViewForm() {
		return userDTo;
	}

}