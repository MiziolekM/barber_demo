package dev.mateusz.barber.demo.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.mateusz.barber.demo.dao.OrderDao;
import dev.mateusz.barber.demo.dao.UserDao;
import dev.mateusz.barber.demo.dto.DtoOrder;
import dev.mateusz.barber.demo.entity.Order;
import dev.mateusz.barber.demo.entity.User;
import javassist.bytecode.stackmap.TypeData.ClassName;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private UserDao userDao;

	@Override
	@Transactional
	public void saveOrder(DtoOrder theDtoOrder) {
		
		Order order = new Order();
		
		String service = theDtoOrder.getService();
		int price = 0;
		
		switch (service) {
		case "Strzyżenie głowy":
	    	price = 30;
	        break;
		 case "Strzyżenie brody":
	    	price = 20;
	        break;
		case "Komplet":
	    	price = 50;
	        break;
	    case "Royal Special":
	    	price = 80;
	        break;
		}
		
		User user = userDao.findByUserName(theDtoOrder.getUserName());
		
		String sDate1 = theDtoOrder.getDate();  
	    Date date1 = null;
		try {
			date1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse(sDate1);
		} catch (ParseException e) {
			e.printStackTrace();
		}  

		Calendar cal = Calendar.getInstance();
	    cal.setTime(date1);
	    int month      = cal.get(Calendar.MONTH); // Jan = 0, dec = 11
		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		int hourOfDay  = cal.get(Calendar.HOUR_OF_DAY); // 24 hour clock
		
	    Calendar finalCal = new GregorianCalendar(2019,month,dayOfMonth,hourOfDay,0,0);
	    
	    Date finalDate = finalCal.getTime();
		order.setIdOrder(theDtoOrder.getIdOrder());
		order.setService(service);
		order.setPrice(price);
		order.setStatus(theDtoOrder.getStatus());
		order.setDate(finalDate);
		order.setUser(user);
		
		orderDao.saveOrder(order);
	}

	@Override
	@Transactional
	public List<Order> getOrders() {
		return orderDao.getOrders();
	}

	@Override
	@Transactional
	public void deleteOrder(int theId) {
		orderDao.deleteOrder(theId);
	}

	@Override
	@Transactional
	public List<Order> searchOrder(String theSearchName) {
		return orderDao.searchOrder(theSearchName);
	}

	@Override
	@Transactional
	public Order getOrderById(int theId) {
		return orderDao.getOrderById(theId);
	}
	
	/*
	 * Metoda ma na celu wygenerowanie dat na następne 20 dni
	 * dla godzin od 10 do 18 z wyłączeniem tych dat dla których już isnieje 
	 * zamówienie.
	 * @see dev.mateusz.barber.demo.service.OrderService#getPrepDates()
	 */
	
	@Override
	@Transactional
	public LinkedList<Date> getPrepDates() {

		// lista przygotowanych dat
		LinkedList<Date> prepDates = new LinkedList<Date>();
		// lista z datami już zarezerwowanymi
		LinkedList<Date> datesFromOrders = new LinkedList<Date>();

		// lista z zamówieniami
		List<Order> orders = orderDao.getOrders();
		
		// przygotowanie formatu i aktualnego dnia oraz miesiąca
		Date toPrepDate = new Date();
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
		Calendar calendar = new GregorianCalendar();
		int month = calendar.get(Calendar.MONTH); // Jan = 0, dec = 11
		int actDay = calendar.get(Calendar.DAY_OF_MONTH);
		
		// zmienne pomocnicze
		int numberOfRecordedDays = 20;
		int maxDay = 0;
		int day = 0;
		boolean firsttime = true;

		
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

		// usuwanie tych dat które już są zarezerwowane
		for (Iterator<Date> i = datesFromOrders.iterator(); i.hasNext();) {
			Date tempDate = (Date) i.next();
			prepDates.remove(tempDate);
		}

		return prepDates;
	}

	

}
