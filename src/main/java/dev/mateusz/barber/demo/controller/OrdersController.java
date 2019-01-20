package dev.mateusz.barber.demo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dev.mateusz.barber.demo.dto.DtoOrder;
import dev.mateusz.barber.demo.entity.Order;
import dev.mateusz.barber.demo.service.OrderService;

@Controller
@RequestMapping("/orders")
public class OrdersController {
	
	@Autowired
	private OrderService orderService;

	@GetMapping("/list")
	public String listOrder(Model theModel) {
		
		List<Order> theOrders = orderService.getOrders();
		
		theModel.addAttribute("orders", theOrders);
		
		return "list-orders";
	}

	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("idOrder") int theId) {
		
		orderService.deleteOrder(theId);
		
		return "redirect:/orders/list";
	}
	
	@PostMapping("/updateOrder")
	public String updateOrder(@Valid @ModelAttribute("dtoOrder") DtoOrder theDtoOrder,
			Model theModel) {
		
     	orderService.saveOrder(theDtoOrder);
     	
		return "redirect:/";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("idOrder") int theId, Model theModel) {
		
		Order order = orderService.getOrderById(theId);
		
		DtoOrder dtoOrder = new DtoOrder();
		
		dtoOrder.setDate(order.getDate().toString());
		dtoOrder.setIdOrder(order.getIdOrder());
		dtoOrder.setPrice(order.getPrice());
		dtoOrder.setService(order.getService());
		dtoOrder.setStatus(order.getStatus());
		dtoOrder.setUserName(order.getUser().getUserName());
		
		LinkedList<Date> prepDates = orderService.getPrepDates();
		
	    Date actualDate = null;
		try {
			actualDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S", Locale.US).parse(dtoOrder.getDate());
		} catch (ParseException e) {
			
			e.printStackTrace();
		}  
		
		prepDates.addFirst(actualDate);
		
		ArrayList<String> prepService = new ArrayList<>();
		
		switch (dtoOrder.getService()) {
	    case "Strzyżenie głowy":
	    	prepService.add("Strzyżenie głowy");
	    	prepService.add("Strzyżenie brody");
	    	prepService.add("Komplet");
	    	prepService.add("Royal Special");
	        break;
	    case "Strzyżenie brody":
	    	prepService.add("Strzyżenie brody");
	    	prepService.add("Strzyżenie głowy");
	    	prepService.add("Komplet");
	    	prepService.add("Royal Special");
	        break;
	    case "Komplet":
	    	prepService.add("Komplet");
	    	prepService.add("Strzyżenie głowy");
	    	prepService.add("Strzyżenie brody");
	    	prepService.add("Royal Special");
	        break;
	    case "Royal Special":
	    	prepService.add("Royal Special");
	    	prepService.add("Strzyżenie głowy");
	    	prepService.add("Strzyżenie brody");
	    	prepService.add("Komplet");
	        break;
		}
		
		theModel.addAttribute("prepDates", prepDates);
		theModel.addAttribute("dtoOrder", dtoOrder);
		theModel.addAttribute("prepService", prepService);
		
		return "order-form";
	}
	
	@PostMapping("/search")
	public String searchUser(@RequestParam("theSearchName") String theSearchName, Model theModel) {

		List<Order> orders = orderService.searchOrder(theSearchName);

		theModel.addAttribute("orders", orders);

		return "list-orders";
	}
}
