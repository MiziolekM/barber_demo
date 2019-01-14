package dev.mateusz.barber.demo.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
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

import dev.mateusz.barber.demo.dto.CrmUser;
import dev.mateusz.barber.demo.dto.DtoOrder;
import dev.mateusz.barber.demo.entity.Order;
import dev.mateusz.barber.demo.entity.User;
import dev.mateusz.barber.demo.service.OrderService;
import javassist.bytecode.stackmap.TypeData.ClassName;

@Controller
public class HomeController {

	@Autowired
	private OrderService orderService;

	// tak naprawdę nigdzie nie wprowadzam danych za pomocą inputa ale możliwe że zmienie coś to na razie zostawiam
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {

		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);

	}

	// to zmienie kiedyś - > funnkcja do przygotowania dat
	private LinkedList<Date> getPrepDates() {

		// lista przygotowanych dat
		LinkedList<Date> prepDates = new LinkedList<Date>();
		
		// lista z datami już zarezerwowanymi
		LinkedList<Date> datesFromOrders = new LinkedList<Date>();

		// lista z zamówieniami
		List<Order> orders = orderService.getOrders();
		
		// przygotowanie formatu i aktualnego dnia oraz miesiąca
		Date toPrepDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
		Calendar calendar = new GregorianCalendar();
		int month = calendar.get(Calendar.MONTH); // Jan = 0, dec = 11
		int actDay = calendar.get(Calendar.DAY_OF_MONTH);
		
		// zmienne pomocnicze
		int numberOfRecordedDays = 20;
		int maxDay = 0;
		int day = 0;
		boolean firsttime = true;

		Logger logger = Logger.getLogger(ClassName.class.getName());
		
		// dodanie dat do listy datesFromOrders
		for (Order order : orders) {
			Date date = (Date) order.getDate();
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			Date finalDate = cal.getTime();
			
			datesFromOrders.add(finalDate);
		}
		
		// właściwe przygotowanie formatu
		calendar = new GregorianCalendar(2019,month,actDay,9,0,0);
		// pobranie daty
		toPrepDate = calendar.getTime();
		
		logger.info("---------------<> " + sdf.format(toPrepDate));

		// określenie ilości dni dla danego miesiąca
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

		// logika dodawanie dat na 20 dni
		outerLoop: for (int m = 0; m <= 2; m++) {

			if (firsttime) {
				day = actDay + 1;
			} else {
				day = 1;
			}

			// logger.info("==============================Miesiąc=======> " + month);

			for (int i = 0; i <= numberOfRecordedDays; i++) {

				// logger.info("==============================Dzień=======> " + day);

				for (int j = 0; j <= 8; j++) {

					//int hour = 10 + j;
					
					Calendar cal = Calendar.getInstance();
					cal.setTime(toPrepDate);
					cal.add(Calendar.HOUR_OF_DAY, 1);
					toPrepDate = cal.getTime();
					
					logger.info("======> " + sdf.format(toPrepDate));
					
					prepDates.add(toPrepDate);

					// czyli 21 dni
					if (prepDates.size() >= 189) {
						break outerLoop;
					}

				}
				
				logger.info("------------------------------");
				
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

		// usuwanie tych dat które już są zarezerwowane
		for (Iterator<Date> i = datesFromOrders.iterator(); i.hasNext();) {
			Date tempDate = (Date) i.next();
			prepDates.remove(tempDate);
		}

		return prepDates;
	}

	@GetMapping("/")
	public String showHome(Model theModel) {
		LinkedList<Date> prepDates = getPrepDates();

		theModel.addAttribute("dtoOrder", new DtoOrder());
		theModel.addAttribute("prepDates", prepDates);

		return "home";
	}

	@RequestMapping("/showLoginPage")
	public String showLoginPage() {
		return "fancy-login";
	}

	@RequestMapping("/leaders")
	public String showLeaders() {
		return "leaders";
	}

	@RequestMapping("/logout")
	public String showLoginPageAgain() {
		return "fancy-login";
	}

	@PostMapping("/saveOrder")
	public String saveOrder(@ModelAttribute("dtoOrder") DtoOrder theDtoOrder) {

		Logger logger = Logger.getLogger(ClassName.class.getName());

		theDtoOrder.setStatus("zapisany");
		orderService.saveOrder(theDtoOrder);

		logger.info("==============>" + theDtoOrder.toString());

		return "order-confirmation";
	}

}
