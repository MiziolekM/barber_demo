package dev.mateusz.barber.demo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

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
import javassist.bytecode.stackmap.TypeData.ClassName;

@Controller
@RequestMapping("/orders")
public class OrdersController {
	
	@Autowired
	private OrderService orderService;
	
	// to zmienie kiedyś - > funnkcja do przygotowania dat
	private LinkedList<Date> getPrepDates() {
		

		LinkedList<Date> prepDates = new LinkedList<Date>();
		LinkedList<Date> datesFromOrders = new LinkedList<Date>();

		List<Order> orders = orderService.getOrders();

		Logger logger = Logger.getLogger(ClassName.class.getName());
		
		Date toPrepDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
		Calendar calendar = new GregorianCalendar();
		int month = calendar.get(Calendar.MONTH); // Jan = 0, dec = 11
		int actDay = calendar.get(Calendar.DAY_OF_MONTH); 
		
		int numberOfRecordedDays = 20;
		int maxDay = 0;
		int day = 0;
		boolean firsttime = true;
		
		calendar = new GregorianCalendar(2019,month,actDay,9,0,0);

		for (Order order : orders) {
			Date date = (Date) order.getDate();
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			//cal.add(Calendar.HOUR_OF_DAY, -1);
			Date finalDate = cal.getTime();
			
			datesFromOrders.add(finalDate);
		}
		
		logger.info("------------------------------");
		
		calendar = new GregorianCalendar(2019,month,actDay,9,0,0);
		
		toPrepDate = calendar.getTime();

		switch (month) {
		case 0:
			maxDay = 31;
			break;
		case 1:
			maxDay = 28;
			break;
		case 2:
			maxDay = 31;
			break;
		case 3:
			maxDay = 30;
			break;
		case 4:
			maxDay = 31;
			break;
		case 5:
			maxDay = 30;
			break;
		case 6:
			maxDay = 31;
			break;
		case 7:
			maxDay = 31;
			break;
		case 8:
			maxDay = 30;
			break;
		case 9:
			maxDay = 31;
			break;
		case 10:
			maxDay = 30;
			break;
		case 11:
			maxDay = 31;
			break;
		}

		outerLoop: for (int m = 0; m <= 2; m++) {

			if (firsttime) {
				day = actDay + 1;
			} else {
				day = 1;
			}

			for (int i = 0; i <= numberOfRecordedDays; i++) {

				for (int j = 0; j <= 8; j++) {

					
					Calendar cal = Calendar.getInstance();
					cal.setTime(toPrepDate);
					cal.add(Calendar.HOUR_OF_DAY, 1);
					toPrepDate = cal.getTime();
					
					prepDates.add(toPrepDate);

					// czyli 21 dni
					if (prepDates.size() >= 189) {
						break outerLoop;
					}

				}
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(toPrepDate);
				cal.add(Calendar.DAY_OF_MONTH, 1);
				cal.add(Calendar.HOUR_OF_DAY, -9);
				toPrepDate = cal.getTime();

				if (day == maxDay) {
					day = 1;
					numberOfRecordedDays = 20 - i;
					firsttime = false;
					break;
				}

				day = day + 1;
			}

			month = month + 1;
		}
		
		for (Iterator<Date> i = datesFromOrders.iterator(); i.hasNext();) {
			Date tempDate = (Date) i.next();
			prepDates.remove(tempDate);
		}

		return prepDates;
	}

	
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
		
		LinkedList<Date> prepDates = getPrepDates();
		
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
}
