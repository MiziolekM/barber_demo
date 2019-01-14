package dev.mateusz.barber.demo.controller;

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

import dev.mateusz.barber.demo.dto.CrmUser;
import dev.mateusz.barber.demo.entity.User;
import dev.mateusz.barber.demo.service.UserService;

@Controller
@RequestMapping("/register")
public class RegistrationController {

	@Autowired
	private UserService userService;

	private Logger logger = Logger.getLogger(getClass().getName());

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

	// @Valid - oznacza wykonywanie zasady walidacyjnej na obiekcie
	// @ModelAttribute("crmUser") CrmUser theCrmUser,
	// BindingResult theBindingResult, - to jest wynik walidacji
	// Model theModel do przekazywania danych do widoku
	@PostMapping("/processRegistrationForm")
	public String processRegistrationForm(@Valid @ModelAttribute("crmUser") CrmUser theCrmUser,
			BindingResult theBindingResult, Model theModel) {

		String userName = theCrmUser.getUserName();
		logger.info("Rejestracja zostaje wykonywana dla użytkownika: " + userName);

		// z walidacji
		if (theBindingResult.hasErrors()) {
			return "registration-form";
		}

		// sprawdzenie czy w bazie danych istnieje już taki user
		User existingUserByUserName = userService.findByUserName(userName);
		if (existingUserByUserName != null) {

			// theModel.addAttribute("crmUser", new CrmUser());

			// ustawiam puste hasła (w sumie to nie musze bo i tak one się wykasują w
			// formularzu)
			// ustawiam nazwą na pusty bo taki już istnieje
			// reszta zostaje zapamiętana
			theCrmUser.setUserName("");
			theCrmUser.setPassword("");
			theCrmUser.setMatchingPassword("");
			// przekazuje do widoku przygotowany crmUser
			theModel.addAttribute("crmUser", theCrmUser);

			theModel.addAttribute("registrationError", "Użytkownik z taką nazwą już istnieje!");

			logger.warning("Użytkownik z taką nazwą już istnieje!");
			return "registration-form";
		}

		int thePhoneNumber = theCrmUser.getPhoneNumber();
		User existingUserByPhoneNumber = userService.findByUserPhoneNumber(thePhoneNumber);
		if (existingUserByPhoneNumber != null) {

			// ustawiam puste hasła (w sumie to nie musze bo i tak one się wykasują w
			// formularzu)
			// ustawiam telefon na pusty bo taki już istnieje
			// reszta zostaje zapamiętana
			theCrmUser.setPhoneNumber(0);
			theCrmUser.setPassword("");
			theCrmUser.setMatchingPassword("");
			// przekazuje do widoku przygotowany crmUser
			theModel.addAttribute("crmUser", theCrmUser);

			theModel.addAttribute("registrationError", "Użytkownik z takim numerem telefonu już istnieje!");

			logger.warning("Użytkownik z takim numerem telefonu już istnieje!");
			return "registration-form";
		}

		String theEmail = theCrmUser.getEmail();
		User existingUserByEmail = userService.findByUserEmail(theEmail);
		if (existingUserByEmail != null) {
			// theModel.addAttribute("crmUser", new CrmUser());

			// ustawiam puste hasła (w sumie to nie musze bo i tak one się wykasują w
			// formularzu)
			// ustawiam email na pusty bo taki już istnieje
			// reszta zostaje zapamiętana
			theCrmUser.setEmail("");
			theCrmUser.setPassword("");
			theCrmUser.setMatchingPassword("");
			// przekazuje do widoku przygotowany crmUser
			theModel.addAttribute("crmUser", theCrmUser);

			theModel.addAttribute("registrationError", "Użytkownik z takim adresem email już istnieje!");

			logger.warning("Użytkownik z takim adresem email już istnieje!");
			return "registration-form";
		}

		// zapisanie użytkownika
		userService.saveUser(theCrmUser);

		logger.info("Pomyslnie zarejestrowano uzytkownika: " + userName);

		return "registration-confirmation";
	}
}