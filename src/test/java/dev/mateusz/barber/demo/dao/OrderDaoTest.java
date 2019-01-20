package dev.mateusz.barber.demo.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dev.mateusz.barber.demo.entity.Order;
import dev.mateusz.barber.demo.entity.Role;
import dev.mateusz.barber.demo.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class OrderDaoTest {
	
	private User user = new User();
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private UserDao userDao;

	@Test
    @Transactional
    @Rollback(true)
    public void testSaveOrder()
    {
		List<Role> roles = new ArrayList<Role>();
		roles.add(new Role("ROLE_CUSTOMER"));
		user = new User("testowy", "testowy", "Test", "Test", 999999999, "test@test.pl", roles);
		userDao.saveUser(user);
		user = userDao.findByUserName("testowy");
		Order order = new Order(new Date(), 30, "zapisany", "Strzyżenie brody", user);
		
		orderDao.saveOrder(order);
         
        List<Order> orders = orderDao.getOrders();
        orders.size();
        
        Assert.assertEquals(order, orders.get(orders.size()-1));
    }
	
	@Test
    @Transactional
    @Rollback(true)
    public void testGetOrders()
    {
		List<Order> orders = orderDao.getOrders();
        Assert.assertNotNull("Order list is empty", orders);
    }
	
	@Test
    @Transactional
    @Rollback(true)
    public void testDeleteOrder()
    {
		List<Order> orders = orderDao.getOrders();
		
		orderDao.deleteOrder(orders.get(orders.size()-1).getIdOrder());
		
		Order order = orderDao.getOrderById(orders.size()-1);
		
        Assert.assertNull(order);
    }
	
	@Test
    @Transactional
    @Rollback(true)
    public void testGetOrderById()
    {
		List<Order> orders = orderDao.getOrders();		
		Order order = orderDao.getOrderById(orders.get(orders.size()-1).getIdOrder());
        
		Assert.assertNotNull(order);
		Assert.assertEquals(order, orders.get(orders.size()-1));
    }
	
	@Test
    @Transactional
    @Rollback(true)
    public void testSearchOrder()
    {

		List<Order> ordersTwo = orderDao.searchOrder("Strzyżenie brody");
		
		Assert.assertNotNull(ordersTwo);
    }

}
