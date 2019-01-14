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

	// wstrzykuje userService
	@Autowired
	private UserService userService;

	// logger
	private Logger logger = Logger.getLogger(getClass().getName());

	// dodaje initbinder ... do konwersji wejsciowych stringow (ciecia od białych
	// znaków)
	// usuwam białe znaki z przodu i konca

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {

		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);

	}

	@GetMapping("/list")
	public String listCustomer(Model theModel) {

		// daj uzytkownikow z service
		List<User> theUsers = userService.getUsers();

		// dodaj uzytkownikow do modelu
		theModel.addAttribute("users", theUsers);

		logger.info("=====>: " + theUsers);

		return "list-customers";
	}

	/*
	 * być moze dodam dodawanie usera przez admina lub moderatora, zostawiam
	 * zakomentowanie mapowanie
	 * 
	 * @GetMapping("/showFormForAdd") public String showFormForAdd(Model theModel) {
	 * 
	 * //utworzenie atrubutu modelu do bindowania formularza danych User theUser =
	 * new User();
	 * 
	 * theModel.addAttribute("crmUser", theUser);
	 * 
	 * return "customer-form"; }
	 */

	@PostMapping("/updateUser")
	public String updateUser(@Valid @ModelAttribute("crmUser") CrmUser theCrmUser, BindingResult theBindingResult,
			Model theModel) {

		if (theBindingResult.hasErrors()) {
			return "customer-form";
		}

		int crmIdUser = theCrmUser.getIdUser();

		// sprawdzenie czy w bazie danych istnieje już taki user
		String userName = theCrmUser.getUserName();
		User existingUserByUserName = userService.findByUserName(userName, crmIdUser);
		if (existingUserByUserName != null) {

			// ustawiam nazwą na pusty bo taki już istnieje
			// reszta zostaje zapamiętana
			theCrmUser.setUserName("");

			// przekazuje do widoku przygotowany crmUser
			theModel.addAttribute("crmUser", theCrmUser);

			// przekazuje do widoku specjalną informacje
			theModel.addAttribute("updateError", "Użytkownik z taką nazwą już istnieje!");

			logger.warning("Użytkownik z taką nazwą już istnieje!");
			return "customer-form";
		}

		String theEmail = theCrmUser.getEmail();
		User existingUserByEmail = userService.findByUserEmail(theEmail, crmIdUser);
		if (existingUserByEmail != null) {

			// ustawiam email na pusty bo taki już istnieje
			// reszta zostaje zapamiętana
			theCrmUser.setEmail("");

			theModel.addAttribute("crmUser", theCrmUser);

			theModel.addAttribute("updateError", "Użytkownik z takim adresem email już istnieje!");

			logger.warning("Użytkownik z takim adresem email już istnieje!");
			return "customer-form";
		}

		int thePhoneNumber = theCrmUser.getPhoneNumber();
		User existingUserByPhoneNumber = userService.findByUserPhoneNumber(thePhoneNumber, crmIdUser);
		if (existingUserByPhoneNumber != null) {

			// ustawiam telefon na pusty bo taki już istnieje
			// reszta zostaje zapamiętana
			theCrmUser.setPhoneNumber(0);

			theModel.addAttribute("crmUser", theCrmUser);

			theModel.addAttribute("updateError", "Użytkownik z takim numerem telefonu już istnieje!");

			logger.warning("Użytkownik z takim numerem telefonu już istnieje!");
			return "customer-form";
		}

		// zapisz usera uzywając servisu
		userService.updateUser(theCrmUser);

		return "redirect:/crm/list";
	}

	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("idUser") int theId, Model theModel) {

		// daj usera z serwisu
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

		Logger logger = Logger.getLogger(getClass().getName());

		logger.info("----->" + crmUser.getRoles());

		// ustaw usera jako model attribute do formularza
		theModel.addAttribute("crmUser", crmUser);

		return "customer-form";
	}

	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("idUser") int theId) {

		// daj customera z serwisu
		userService.deleteUser(theId);

		// zwracamy liste zrefreshowaną
		return "redirect:/crm/list";
	}

	@PostMapping("/search")
	public String searchUser(@RequestParam("theSearchName") String theSearchName, Model theModel) {

		// szukaj uzytkownikow z serwisu
		List<User> theCustomers = userService.searchUsers(theSearchName);

		// dodaj usera do modelu
		theModel.addAttribute("users", theCustomers);

		return "list-customers";
	}

}
