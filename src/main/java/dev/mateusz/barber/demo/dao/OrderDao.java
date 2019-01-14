package dev.mateusz.barber.demo.dao;

import java.util.List;

import dev.mateusz.barber.demo.entity.Order;

public interface OrderDao {
	
	void saveOrder(Order order);
	
	List<Order> getOrders();
	
	void deleteOrder(int theId);
	
	List<Order> searchOrder(String theSearchName);
	
	Order getOrderById(int theId);

}
