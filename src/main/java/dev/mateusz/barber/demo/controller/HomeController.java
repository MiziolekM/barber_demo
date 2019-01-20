package dev.mateusz.barber.demo.controller;

import java.util.Date;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.mateusz.barber.demo.dto.DtoOrder;
import dev.mateusz.barber.demo.service.OrderService;

@Controller
public class HomeController {

	@Autowired
	private OrderService orderService;

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {

		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	@GetMapping("/")
	public String showHome(Model theModel) {
		
		LinkedList<Date> prepDates = orderService.getPrepDates();
		DtoOrder dtoOrder = new DtoOrder();

		theModel.addAttribute("dtoOrder", dtoOrder);
		theModel.addAttribute("prepDates", prepDates);

		return "home";
	}

	@RequestMapping("/showLoginPage")
	public String showLoginPage() {
		return "fancy-login";
	}
	
	@RequestMapping("/logout")
	public String showLoginPageAgain() {
		return "fancy-login";
	}

	@PostMapping("/saveOrder")
	public String saveOrder(@ModelAttribute("dtoOrder") DtoOrder theDtoOrder) {

		theDtoOrder.setStatus("zapisany");
		orderService.saveOrder(theDtoOrder);

		return "order-confirmation";
	}

}
