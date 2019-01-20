package dev.mateusz.barber.demo.service;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import dev.mateusz.barber.demo.dao.OrderDao;
import dev.mateusz.barber.demo.entity.Order;
import dev.mateusz.barber.demo.entity.Role;
import dev.mateusz.barber.demo.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {
	
	@MockBean
	private OrderDao orderDao;
	
	@Autowired
	private OrderService orderService;
	
	@Test
	public void testGetPrepDates() {
		
		LinkedList<Date> prepDatesFromOrders = new LinkedList<Date>();
		
		List<Role> roles = new ArrayList<Role>();
		roles.add(new Role("ROLE_CUSTOMER"));
		User user = new User("testowy", "testowy", "Test", "Test", 999999999, "test@test.pl", roles);
		user.setIdUser(15);
		User user2 = new User("testowy2", "testowy2", "Test", "Test", 999999992, "test2@test.pl", roles);
		user.setIdUser(16);
		List<User> users = new ArrayList<User>();
		users.add(user);
		users.add(user2);
		
		List<Order> orders = new ArrayList<Order>();
		
		Calendar cal = Calendar.getInstance();
		Calendar calendar = null;
		Date date = null;
		
		for(int i = 0 ; i < 10 ; i++) {
			cal.setTime(new Date());
			cal.add(Calendar.HOUR_OF_DAY, i);
			calendar = new GregorianCalendar(2019,cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH),cal.get(Calendar.HOUR_OF_DAY),0,0);
			date = calendar.getTime();
			prepDatesFromOrders.add(date);
			orders.add(new Order(date, 50, "zapisany", "Komplet", user));
		}

		Mockito.when(orderDao.getOrders()).thenReturn(orders);
		
		for(int i = 0 ; i < 10 ; i++) {
		Assert.assertThat(orderService.getPrepDates(), not(hasItem(prepDatesFromOrders.get(i))));
		}
	}

}
