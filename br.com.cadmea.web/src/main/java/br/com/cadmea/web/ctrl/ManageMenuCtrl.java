/**
 * 
 */
package br.com.cadmea.web.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Gilberto Santos
 *
 */
@Controller
public class ManageMenuCtrl {
	
	@RequestMapping(value ="/admin", method = RequestMethod.GET)
	public String welcome() {
		return "index";
	}
	
	@RequestMapping("/admin/managerPermission")
	public ModelAndView index(){
		ModelAndView mv = new ModelAndView("index");
		mv.addObject("page", "managerPermission");
		return mv;
	}
	
	@RequestMapping("/login")
	public String login(){
		return "loginForm";
	}
	
	@RequestMapping("/logout")
	public String logout(){
		return "loginForm";
	}
	
}
