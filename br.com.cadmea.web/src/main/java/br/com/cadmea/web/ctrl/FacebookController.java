/**
 * 
 */
package br.com.cadmea.web.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Gilberto Santos
 *
 */
@Controller
public class FacebookController {
	
	@Autowired
	private Facebook facebook;
	
	@Autowired
    private ConnectionRepository connectionRepository;
	
	@RequestMapping("/signin/facebook")
	public String loginFacebook(Model model) {
		
		if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
            return "redirect:/connect/facebook";
        }
		
		String [] fields = { "id", "email",  "first_name", "last_name", "link", "picture" };
		User userProfile = facebook.fetchObject("me", User.class, fields);
		
		model.addAttribute("first_name", userProfile.getFirstName() );
		model.addAttribute("last_name", userProfile.getLastName() );
		model.addAttribute("id", userProfile.getId());
		model.addAttribute("link", userProfile.getLink());
		model.addAttribute("email", userProfile.getEmail());
		model.addAttribute("picture", facebook.getBaseGraphApiUrl() + userProfile.getId() + "/picture");
		
		return "admin";
	}
	
	
}