package dev.mateusz.barber.demo.service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import dev.mateusz.barber.demo.dto.DtoOrder;
import dev.mateusz.barber.demo.entity.Order;

public interface OrderService {
	
	void saveOrder(DtoOrder theDtoOrder);
	
	List<Order> getOrders();
	
	void deleteOrder(int theId);
	
	List<Order> searchOrder(String theSearchName);
	
	Order getOrderById(int theId);
	
	LinkedList<Date> getPrepDates();

}
