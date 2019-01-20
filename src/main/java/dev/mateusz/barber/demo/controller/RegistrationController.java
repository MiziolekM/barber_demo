package dev.mateusz.barber.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.mateusz.barber.demo.dto.CrmUser;
import dev.mateusz.barber.demo.entity.User;
import dev.mateusz.barber.demo.service.UserService;

@Controller
@RequestMapping("/register")
public class RegistrationController {

	@Autowired
	private UserService userService;

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {

		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	@GetMapping("/showRegistrationForm")
	public String showMyLoginPage(Model theModel) {

		theModel.addAttribute("crmUser", new CrmUser());

		return "registration-form";
	}

	@PostMapping("/processRegistrationForm")
	public String processRegistrationForm(@Valid @ModelAttribute("crmUser") CrmUser theCrmUser,
			BindingResult theBindingResult, Model theModel) {

		String userName = theCrmUser.getUserName();

		if (theBindingResult.hasErrors()) {
			return "registration-form";
		}
		
		User existingUserByUserName = userService.findByUserName(userName);
		if (existingUserByUserName != null) {

			theCrmUser.setUserName("");
			theCrmUser.setPassword("");
			theCrmUser.setMatchingPassword("");
			theModel.addAttribute("crmUser", theCrmUser);

			theModel.addAttribute("registrationError", "Użytkownik z taką nazwą już istnieje!");

			return "registration-form";
		}

		int thePhoneNumber = theCrmUser.getPhoneNumber();
		User existingUserByPhoneNumber = userService.findByUserPhoneNumber(thePhoneNumber);
		if (existingUserByPhoneNumber != null) {

			theCrmUser.setPhoneNumber(0);
			theCrmUser.setPassword("");
			theCrmUser.setMatchingPassword("");
			theModel.addAttribute("crmUser", theCrmUser);

			theModel.addAttribute("registrationError", "Użytkownik z takim numerem telefonu już istnieje!");

			return "registration-form";
		}

		String theEmail = theCrmUser.getEmail();
		User existingUserByEmail = userService.findByUserEmail(theEmail);
		if (existingUserByEmail != null) {
			
			theCrmUser.setEmail("");
			theCrmUser.setPassword("");
			theCrmUser.setMatchingPassword("");
			theModel.addAttribute("crmUser", theCrmUser);

			theModel.addAttribute("registrationError", "Użytkownik z takim adresem email już istnieje!");

			return "registration-form";
		}

		userService.saveUser(theCrmUser);


		return "registration-confirmation";
	}
}