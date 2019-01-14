package dev.mateusz.barber.demo.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.mateusz.barber.demo.dao.OrderDao;
import dev.mateusz.barber.demo.dao.UserDao;
import dev.mateusz.barber.demo.dto.DtoOrder;
import dev.mateusz.barber.demo.entity.Order;
import dev.mateusz.barber.demo.entity.User;

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
		
		Logger logger = Logger.getLogger(getClass().getName());
		
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
		
		logger.info("-------------------------------------------> " +sDate1+"\t"+date1);
		

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
		
		logger.info("---------order------> " +order.toString());
		
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

}
