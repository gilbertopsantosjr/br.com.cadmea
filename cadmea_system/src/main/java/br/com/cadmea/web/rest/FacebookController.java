/**
 * 
 */
package br.com.cadmea.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import br.com.cadmea.comuns.exceptions.BusinessException;
import br.com.cadmea.comuns.orm.enums.Gender;
import br.com.cadmea.comuns.orm.enums.Relationship;
import br.com.cadmea.comuns.util.DateUtil;
import br.com.cadmea.comuns.util.ValidadorUtil;
import br.com.cadmea.dto.UserFormDto;
import br.com.cadmea.model.orm.Person;
import br.com.cadmea.model.orm.SocialNetwork;
import br.com.cadmea.model.orm.UserSystem;
import br.com.cadmea.web.business.SocialNetworkSrv;
import br.com.cadmea.web.business.UserSrv;

/**
 * @author Gilberto Santos
 *
 */
@RestController
@RequestMapping("/social/signin/facebook")
public class FacebookController {
	
	@Autowired
	private Facebook facebook;
	
	@Autowired
    private ConnectionRepository connectionRepository;
	
	@Autowired
	private HttpServletRequest servletRequest;
	
	@Autowired
	private SocialNetworkSrv socialNetworkSrv;
	
	@Autowired
	private UserSrv userSrv;
	
	@GetMapping 
	public String loginFacebook(Model model) throws URISyntaxException, ParseException {
		
		if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
            return "redirect:/connect/facebook";
        }
		
		String [] fields = { "id", "email",  "first_name", "last_name", "link", "picture" };
		User userProfile = facebook.fetchObject("me", User.class, fields);
		
		if(userProfile == null)
			throw new BusinessException("can't find out the user on facebook");
		
		UserSystem userSystem = new UserSystem();
		SocialNetwork socialNetwork = socialNetworkSrv.findRegistered(userProfile.getId());
		
		if(socialNetwork == null){
			
			socialNetwork = new SocialNetwork();
			
			if(ValidadorUtil.isValid(userProfile.getId()) )
				socialNetwork.setIdNetwork(userProfile.getId());
			
			if(ValidadorUtil.isValid(userProfile.getLink()) )
				socialNetwork.setLink(userProfile.getLink());
			
			if(ValidadorUtil.isValid(userProfile.getEmail()) )
				socialNetwork.setPrimaryContact(userProfile.getEmail());
			
			if(ValidadorUtil.isValid(facebook.getBaseGraphApiUrl()) )
				socialNetwork.setPictureProfile(facebook.getBaseGraphApiUrl() + userProfile.getId() + "/picture");
			
			model.addAttribute("socialNetwork", socialNetwork);
			
			userSystem.setEmail(userProfile.getEmail());
			userSystem.setNickname(userProfile.getFirstName());
			
			Person person = new Person();
			person.setName(userProfile.getFirstName());
			person.setSurname(userProfile.getLastName());
			
			userSystem.setPerson(person);
			
			model.addAttribute("userSystem", userSystem);
			model.addAttribute("messages", "Are you new around ? ");
			
			return "register"; 
		}
		
		// prepare the request
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept-Charset", "UTF-8");
		
      
        userSystem.setEmail(userProfile.getEmail());
        
        socialNetwork.setUserSystem(userSystem);
        
        UserFormDto body = new UserFormDto();
        body.setEntity(userSystem);
        
        socialNetworkSrv.save(socialNetwork);
        
        HttpEntity<UserFormDto> request = new HttpEntity<UserFormDto>(body, headers);
        
        URI uri= new URI( servletRequest.getAttribute("contextPath") + ":" + servletRequest.getServerPort() + "/api/public/user/authentication/");
        
        // call the REST Service and get response 
        RestTemplate rest = new RestTemplate();
        ResponseEntity<UserFormDto> response = rest.postForEntity(uri, request, UserFormDto.class);
		response.getBody().setPictureProfile(socialNetwork.getPictureProfile());
		response.getBody().getEntity().setEmail(userProfile.getEmail());
		
		final Person person = new Person();
		person.setName(userProfile.getFirstName());
		person.setSurname(userProfile.getLastName());
		
		response.getBody().getEntity().setPerson(person);
		
		model.addAttribute("userSystem", response.getBody() );
		
		return "admin";
	}
	
	
}