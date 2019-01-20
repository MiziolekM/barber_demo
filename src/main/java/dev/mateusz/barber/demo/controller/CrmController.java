package dev.mateusz.barber.demo.controller;

import java.util.List;
import java.util.logging.Logger;

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
import org.springframework.web.bind.annotation.RequestParam;

import dev.mateusz.barber.demo.dto.CrmUser;
import dev.mateusz.barber.demo.entity.User;
import dev.mateusz.barber.demo.service.UserService;

@Controller
@RequestMapping("/crm")
public class CrmController {
	
	@Autowired
	private UserService userService;

	private Logger logger = Logger.getLogger(getClass().getName());

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {

		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);

	}

	@GetMapping("/list")
	public String listCustomer(Model theModel) {

		List<User> theUsers = userService.getUsers();
		theModel.addAttribute("users", theUsers);

		return "list-customers";
	}

	@PostMapping("/updateUser")
	public String updateUser(@Valid @ModelAttribute("crmUser") CrmUser theCrmUser, 
			BindingResult theBindingResult,
			Model theModel) {

		if (theBindingResult.hasErrors()) {
			return "customer-form";
		}

		int crmIdUser = theCrmUser.getIdUser();

		String userName = theCrmUser.getUserName();
		User existingUserByUserName = userService.findByUserName(userName, crmIdUser);
		if (existingUserByUserName != null) {

			theCrmUser.setUserName("");

			theModel.addAttribute("crmUser", theCrmUser);

			theModel.addAttribute("updateError", "Użytkownik z taką nazwą już istnieje!");

			logger.warning("Użytkownik z taką nazwą już istnieje!");
			return "customer-form";
		}

		String theEmail = theCrmUser.getEmail();
		User existingUserByEmail = userService.findByUserEmail(theEmail, crmIdUser);
		if (existingUserByEmail != null) {

			theCrmUser.setEmail("");

			theModel.addAttribute("crmUser", theCrmUser);

			theModel.addAttribute("updateError", "Użytkownik z takim adresem email już istnieje!");

			logger.warning("Użytkownik z takim adresem email już istnieje!");
			return "customer-form";
		}

		int thePhoneNumber = theCrmUser.getPhoneNumber();
		User existingUserByPhoneNumber = userService.findByUserPhoneNumber(thePhoneNumber, crmIdUser);
		if (existingUserByPhoneNumber != null) {

			theCrmUser.setPhoneNumber(0);

			theModel.addAttribute("crmUser", theCrmUser);

			theModel.addAttribute("updateError", "Użytkownik z takim numerem telefonu już istnieje!");

			logger.warning("Użytkownik z takim numerem telefonu już istnieje!");
			return "customer-form";
		}

		userService.updateUser(theCrmUser);

		return "redirect:/crm/list";
	}

	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("idUser") int theId, Model theModel) {

		User theUser = userService.getUserById(theId);

		CrmUser crmUser = new CrmUser();

		crmUser.setIdUser(theUser.getIdUser());
		crmUser.setUserName(theUser.getUserName());
		crmUser.setPassword(theUser.getPassword());
		crmUser.setMatchingPassword(theUser.getPassword());
		crmUser.setFirstName(theUser.getFirstName());
		crmUser.setLastName(theUser.getLastName());
		crmUser.setEmail(theUser.getEmail());
		crmUser.setPhoneNumber(theUser.getPhoneNumber());
		crmUser.setRoles(theUser.getRoles());

		theModel.addAttribute("crmUser", crmUser);

		return "customer-form";
	}

	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("idUser") int theId) {

		userService.deleteUser(theId);

		return "redirect:/crm/list";
	}

	@PostMapping("/search")
	public String searchUser(@RequestParam("theSearchName") String theSearchName, Model theModel) {

		List<User> theCustomers = userService.searchUsers(theSearchName);

		theModel.addAttribute("users", theCustomers);

		return "list-customers";
	}

}
