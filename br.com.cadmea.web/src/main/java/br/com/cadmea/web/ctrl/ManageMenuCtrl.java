/**
 * 
 */
package br.com.cadmea.web.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Gilberto Santos
 *
 */
@Controller
public class ManageMenuCtrl {
	
	@RequestMapping(value ="/")
	public String welcome() {
		return "index";
	}

	@RequestMapping("/register")
	public String register(){
		return "register";
	}
	
	@RequestMapping("/login")
	public String login(){
		return "login";
	}
	
	@RequestMapping("/forgot")
	public String forgot(){
		return "forgot";
	}
	
	@RequestMapping("/logout")
	public String logout(){
		return "logout";
	}
	
}
