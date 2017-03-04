/**
 * 
 */
package br.com.cadmea.web.ctrl;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.cadmea.comuns.orm.enums.Gender;
import br.com.cadmea.comuns.orm.enums.Relationship;
import br.com.cadmea.comuns.orm.enums.Situation;
import br.com.cadmea.model.orm.Person;
import br.com.cadmea.model.orm.UserSystem;
import br.com.cadmea.web.business.UserSrv;

/**
 * @author Gilberto Santos
 */
//@Controller
public class ManagerUserCtrl {

	//@Inject
	private UserSrv userSrv;

	private static final List<Situation> situationList = Arrays.asList(Situation.values());

	private static final List<Gender> genderList = Arrays.asList(Gender.values());

	private static final List<Relationship> relationshipList = Arrays.asList(Relationship.values());

	//@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	//@RequestMapping("/admin/listUser")
	public ModelAndView managerUser() {
		ModelAndView mv = new ModelAndView("index");
		mv.addObject("page", "listUser");
		mv.addObject("users", userSrv.listAll());
		return mv;
	}

	//@RequestMapping("/admin/formUser")
	public ModelAndView formUser() {
		ModelAndView mv = factoryForm(null);
		return mv;
	}

	//@RequestMapping("/admin/updateUser/{id}")
	public ModelAndView updateUser(@PathVariable Long id) {
		UserSystem userSystem = userSrv.find(id);
		ModelAndView mv = factoryForm(userSystem);
		return mv;
	}

	//@RequestMapping("/admin/saveUser")
	public ModelAndView saveUser(@Valid @ModelAttribute UserSystem userSystem, BindingResult result, Model m) {
		ModelAndView mv = factoryForm(userSystem);
		if (result.hasErrors()) {
			mv.addObject("mgs", result.getAllErrors());
			mv.addObject("page", "formUser");
		} else {
			userSrv.insert(userSystem);
			mv.addObject("page", "listUser");
			mv.addObject("users", userSrv.listAll());
		}
		return mv;
	}

	//@RequestMapping("/admin/excludeUser/{id}")
	public String excludeUser(@PathVariable Long id) {
		UserSystem entidade = userSrv.find(id);
		userSrv.remove(entidade);
		return "redirect:/admin/listUser";
	}

	private ModelAndView factoryForm(UserSystem userSystem) {
		// is better clear userSystem than create a new instance
		if (userSystem == null) {
			userSystem = new UserSystem();
			userSystem.setPerson(new Person());
		}

		ModelAndView mv = new ModelAndView("index", "userSystem", userSystem);
		mv.addObject("page", "formUser");
		mv.addObject("situationList", situationList);
		mv.addObject("genderList", genderList);
		mv.addObject("relationshipList", relationshipList);
		return mv;
	}

}
