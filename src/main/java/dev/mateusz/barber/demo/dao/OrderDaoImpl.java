package dev.mateusz.barber.demo.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dev.mateusz.barber.demo.entity.Order;

@Repository
public class OrderDaoImpl implements OrderDao {

	private EntityManager entityManager;

	@Autowired
	public OrderDaoImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void saveOrder(Order order) {
		Session currentSession = entityManager.unwrap(Session.class);

		currentSession.saveOrUpdate(order);

	}

	@Override
	public List<Order> getOrders() {
		Session currentSession = entityManager.unwrap(Session.class);

		Query<Order> theQuery = currentSession.createQuery("from Order order by date", Order.class);
		List<Order> orders = theQuery.getResultList();

		return orders;
	}

	@Override
	public void deleteOrder(int theId) {
		Session currentSession = entityManager.unwrap(Session.class);

		Query theQuery = currentSession.createQuery("delete from Order where idOrder=:theId");
		theQuery.setParameter("theId", theId);

		theQuery.executeUpdate();
	}

	@Override
	public List<Order> searchOrder(String theSearchName) {
		
		Session currentSession = entityManager.unwrap(Session.class);

		Query<Order> theQuery = null;

		if (theSearchName != null && theSearchName.trim().length() > 0) {

			theQuery = currentSession.createQuery(
					"from Order where lower(service) like :theName or lower(status) like :theName order by date",
					Order.class);
			theQuery.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");
		} else {
			
			theQuery = currentSession.createQuery("from Order order by date", Order.class);
		}

		List<Order> orders = theQuery.getResultList();

		return orders;
	}

	@Override
	public Order getOrderById(int theId) {
		
		Session currentSession = entityManager.unwrap(Session.class);

		Order theOrder = currentSession.get(Order.class, theId);

		return theOrder;
	}

}
