package br.com.cadmea.web.rest;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.cadmea.comuns.util.ValidadorUtil;
import br.com.cadmea.dto.UserFormDto;
import br.com.cadmea.model.orm.CadmeaSystem;
import br.com.cadmea.model.orm.PasswordResetToken;
import br.com.cadmea.model.orm.UserSystem;
import br.com.cadmea.spring.rest.GenericRestService;
import br.com.cadmea.spring.rest.ServicePath;
import br.com.cadmea.spring.rest.exceptions.NotFoundException;
import br.com.cadmea.spring.rest.exceptions.PreconditionRequiredException;
import br.com.cadmea.spring.rest.exceptions.RestException;
import br.com.cadmea.spring.security.orm.UserAccess;
import br.com.cadmea.spring.util.GenericResponse;
import br.com.cadmea.spring.util.SmtpEmailSender;
import br.com.cadmea.web.business.CadmeaSystemSrv;
import br.com.cadmea.web.business.UserSrv;

/**
 * @author Gilberto Santos
 *
 */
@RestController
@RequestMapping(path = ServicePath.PUBLIC_ROOT_PATH + "/user")
public class UserRestSrv extends GenericRestService<UserSystem, UserFormDto> {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Inject
	private UserSrv userSrv;
	
	@Inject
	private CadmeaSystemSrv cadmeaSystemSrv;

	@Inject
	private SmtpEmailSender smtpEmailSender;

	private UserFormDto userDTo;

	private BCryptPasswordEncoder passwordEncoder;

	@Override
	protected void beforeLoadClass() {
		userDTo = new UserFormDto();
		passwordEncoder = new BCryptPasswordEncoder();
	}

	@Override
	protected void beforeSave() {
		final String hashPassword = passwordEncoder.encode(getViewForm().getEntity().getPassword());
		getViewForm().getEntity().setPassword(hashPassword);
	}

	/**
	 * 
	 * @param formDto
	 * @return
	 */
	@RequestMapping(value = "/authentication/", method = RequestMethod.POST)
	public ResponseEntity<UserFormDto> logIn(@RequestBody UserFormDto formDto) {
		logger.info("starting logIn service");

		isValidRequest(formDto);

		UserFormDto found = new UserFormDto();
		try {
			CadmeaSystem cadmeaSystem =  cadmeaSystemSrv.findBy(formDto.getSystemName());
			if(cadmeaSystem == null) 
				throw new NotFoundException("system.not.found");
			
			final String email = formDto.getEntity().getEmail();
			UserSystem userSystem = getService().getUserBy(email);

			if (userSystem == null)
				throw new NotFoundException("user.not.found");

			final Long sysId = cadmeaSystem.getId();
			
			userSystem = getService().getUserBy(email, sysId);
			if(userSystem == null)
				throw new NotFoundException("user.not.allow.in.system");
			
			found.setEntity(userSystem);
			found.setUrl(cadmeaSystem.getUlr());
			
			UserAccess userAccess = new UserAccess(userSystem.getPerson().getName());
			userSystem.getPermissions().forEach(a -> {
				userAccess.getRoles().add(a.getRole());
			});

			Authentication auth = new UsernamePasswordAuthenticationToken(userAccess, null,
					userAccess.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(auth);

		} catch (NotFoundException e) {
			throw e;

		} catch (Exception e) {
			throw new RestException(e);
		}

		return new ResponseEntity<UserFormDto>(found, HttpStatus.OK);
	}

	/**
	 * check if the {@link UserFormDto} is a valid on request
	 * 
	 * @param formDto
	 */
	private void isValidRequest(UserFormDto formDto) {
		if (!ValidadorUtil.isValid(formDto) || !ValidadorUtil.isValid(formDto.getEntity()))
			throw new PreconditionRequiredException("Invalid Object !");
		
		if (!ValidadorUtil.isValid(formDto.getSystemName()))
			throw new PreconditionRequiredException("System name is required !");

		if ( !ValidadorUtil.isValid(formDto.getEntity().getEmail()) )
			throw new PreconditionRequiredException("Username is required !");
		else if ( !ValidadorUtil.isValidEmail(formDto.getEntity().getEmail()) )
			throw new PreconditionRequiredException("Email must be valid !"); 
			
		if ( !ValidadorUtil.isValid(formDto.getEntity().getPassword()) )
			throw new PreconditionRequiredException("Password is required !");
	}

	/**
	 * 
	 * @param request
	 * @param userEmail
	 * @return
	 */
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public GenericResponse recoveryPassword(HttpServletRequest request, @RequestParam("email") String userEmail) {
		logger.info("starting recoveryPassword service");

		UserSystem user = userSrv.getUserBy(userEmail);
		if (user == null) {
			throw new NotFoundException("user");
		}

		String token = UUID.randomUUID().toString();
		userSrv.createPasswordResetTokenForUser(user, token);

		String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

		final String to = user.getEmail();
		final String subject = "Recovery password";
		final Map<String, Object> msg = new HashMap<String, Object>();

		appUrl = appUrl + "/api/private/user/changePassword?id=" + user.getId() + "&token=" + token;

		msg.put("url", appUrl);
		msg.put("nickname", user.getNickname());
		msg.put("fromNickname", "Cadmea Framework");
		msg.put("message", "Please , don't reply this email");

		smtpEmailSender.send(to, subject, msg, request.getLocale());

		return new GenericResponse("recoveryPassword", "");
	}

	/**
	 * change to the new password
	 *
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/savePassword", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public GenericResponse savePassword(@RequestParam("password") String password) {
		UserSystem user = (UserSystem) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		final String hashPassword = passwordEncoder.encode(password);
		userSrv.changeUserPassword(user, hashPassword);
		return new GenericResponse("message.resetPasswordSuc", "");
	}

	/**
	 * Show the form to allow the user change his password
	 *
	 * @param request
	 * @param id
	 * @param token
	 * @return arguments to allow client build the form
	 */
	@RequestMapping(value = "/showChangePassword", method = RequestMethod.GET)
	public GenericResponse showChangePasswordPage(HttpServletRequest request, @RequestParam("id") long id,
			@RequestParam("token") String token) {
		Locale locale = request.getLocale();
		PasswordResetToken passToken = userSrv.getPasswordResetToken(token);
		UserSystem user = passToken.getUser();

		GenericResponse response = new GenericResponse("Ok");
		response.setLocale(locale);

		if (passToken == null || user.getId() != id) {
			response = new GenericResponse("message.resetPasswordSuc", "auth.message.invalidToken");
		}

		Calendar cal = Calendar.getInstance();
		if ((passToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
			response = new GenericResponse("message.resetPasswordSuc", "auth.message.expired");
		}

		UserAccess userAccess = new UserAccess(user.getPerson().getName());
		user.getPermissions().forEach(a -> {
			userAccess.getRoles().add(a.getRole());
		});

		Authentication auth = new UsernamePasswordAuthenticationToken(userAccess, null, userAccess.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);

		return response;
	}

	@Override
	public UserSrv getService() {
		return userSrv;
	}

	@Override
	public UserFormDto getViewForm() {
		return userDTo;
	}

}