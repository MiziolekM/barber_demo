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

	// wykorzystanie EntityManager
	private EntityManager entityManager;

	// wstrzyknięcie dopiero tu
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
		// dopiero tu używając .unwrap otrzymuje sesje hibernetową
		Session currentSession = entityManager.unwrap(Session.class);

		// utwórz zapytanie !!! sortowanie po nazwisku
		Query<Order> theQuery = currentSession.createQuery("from Order order by date", Order.class);
		List<Order> orders = theQuery.getResultList();

		return orders;
	}

	@Override
	public void deleteOrder(int theId) {
		Session currentSession = entityManager.unwrap(Session.class);

		// usuwam uzytkownika uzywając primary key
		Query theQuery = currentSession.createQuery("delete from Order where idOrder=:theId");
		theQuery.setParameter("theId", theId);

		theQuery.executeUpdate();
	}

	@Override
	public List<Order> searchOrder(String theSearchName) {
		// dopiero tu używając .unwrap otrzymuje sesje hibernetową
		Session currentSession = entityManager.unwrap(Session.class);

		Query<Order> theQuery = null;

		// tylko szukanie po imieniu lub nazwisku jeśli nie jest puste
		if (theSearchName != null && theSearchName.trim().length() > 0) {

			// szukam bez znaczenia wielkości liter (bo generalnie mogę różnie wpisać)
			theQuery = currentSession.createQuery(
					"from Order where lower(date) like :theName or lower(dayOfTheWeek) like :theName or lower(service) like :theName order by date",
					Order.class);
			theQuery.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");
		} else {
			// theSearchName jest pusty ... wiec zwracam wszystkie zamowienia
			theQuery = currentSession.createQuery("from Order order by date", Order.class);
		}

		// execute query and get result list
		List<Order> orders = theQuery.getResultList();

		return orders;
	}

	@Override
	public Order getOrderById(int theId) {
		// dopiero tu używając .unwrap otrzymuje sesje hibernetową
		Session currentSession = entityManager.unwrap(Session.class);

		// odzyskaj/pobierz usera z bazy względem podanego id
		Order theOrder = currentSession.get(Order.class, theId);

		return theOrder;
	}

}
