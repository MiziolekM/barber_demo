package dev.mateusz.barber.demo.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dev.mateusz.barber.demo.entity.User;

@Repository
public class UserDaoImpl implements UserDao {

	// wykorzystanie EntityManager
	private EntityManager entityManager;

	// wstrzyknięcie dopiero tu
	@Autowired
	public UserDaoImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public User findByUserName(String theUserName, int theIdUser) {

		// dopiero tu używając .unwrap otrzymuje sesje hibernetową
		Session currentSession = entityManager.unwrap(Session.class);

		// odzyskuje usera z bazy danych wg username
		Query<User> theQuery = currentSession.createQuery("from User where userName=:theUserName and idUser!=:theIdUser",
				User.class);
		theQuery.setParameter("theUserName", theUserName);
		theQuery.setParameter("theIdUser", theIdUser);

		User theUser = null;
		try {
			theUser = theQuery.getSingleResult();
		} catch (Exception e) {
			theUser = null;
		}

		return theUser;
	}

	@Override
	public void saveUser(User theUser) {

		// dopiero tu używając .unwrap otrzymuje sesje hibernetową
		Session currentSession = entityManager.unwrap(Session.class);

		currentSession.saveOrUpdate(theUser);
	}

	@Override
	public User findByUserPhoneNumber(int thePhoneNumber, int theIdUser) {

		// dopiero tu używając .unwrap otrzymuje sesje hibernetową
		Session currentSession = entityManager.unwrap(Session.class);

		// odzyskuje usera z bazy danych wg numeru telefonu
		Query<User> theQuery = currentSession
				.createQuery("from User where phoneNumber=:thePhoneNumber and idUser!=:theIdUser", User.class);
		theQuery.setParameter("thePhoneNumber", thePhoneNumber);
		theQuery.setParameter("theIdUser", theIdUser);

		User theUser = null;
		try {
			theUser = theQuery.getSingleResult();
		} catch (Exception e) {
			theUser = null;
		}

		return theUser;
	}

	@Override
	public User findByUserEmail(String theEmail, int theIdUser) {

		// dopiero tu używając .unwrap otrzymuje sesje hibernetową
		Session currentSession = entityManager.unwrap(Session.class);

		// odzyskuje usera z bazy danych wg emaila
		Query<User> theQuery = currentSession.createQuery("from User where email=:theEmail and idUser!=:theIdUser",
				User.class);
		theQuery.setParameter("theEmail", theEmail);
		theQuery.setParameter("theIdUser", theIdUser);

		User theUser = null;
		try {
			theUser = theQuery.getSingleResult();
		} catch (Exception e) {
			theUser = null;
		}

		return theUser;
	}

	@Override
	public List<User> getUsers() {

		// dopiero tu używając .unwrap otrzymuje sesje hibernetową
		Session currentSession = entityManager.unwrap(Session.class);

		// utwórz zapytanie !!! sortowanie po nazwisku
		Query<User> theQuery = currentSession
				.createQuery("from User where idUser != 1 and idUser != 2 order by lastName", User.class);
		List<User> users = theQuery.getResultList();

		return users;
	}

	@Override
	public void deleteUser(int theId) {

		// dopiero tu używając .unwrap otrzymuje sesje hibernetową
		Session currentSession = entityManager.unwrap(Session.class);

		// usuwam uzytkownika uzywając primary key
		Query theQuery = currentSession.createQuery("delete from User where idUser=:theId");
		theQuery.setParameter("theId", theId);

		theQuery.executeUpdate();
	}

	@Override
	public List<User> searchUsers(String theSearchName) {

		// dopiero tu używając .unwrap otrzymuje sesje hibernetową
		Session currentSession = entityManager.unwrap(Session.class);

		Query<User> theQuery = null;

		// tylko szukanie po imieniu lub nazwisku jeśli nie jest puste
		if (theSearchName != null && theSearchName.trim().length() > 0) {

			// szukam bez znaczenia wielkości liter (bo generalnie mogę różnie wpisać)
			theQuery = currentSession.createQuery(
					"from User where lower(firstName) like :theName or lower(lastName) like :theName or lower(email) like :theName or lower(userName) like :theName or phoneNumber like :theName",
					User.class);
			theQuery.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");
		} else {
			// theSearchName jest pusty ... wiec zwracam wszystkich uzytkowników
			theQuery = currentSession.createQuery("from User order by lastName", User.class);
		}

		// execute query and get result list
		List<User> users = theQuery.getResultList();

		return users;
	}

	@Override
	public User getUserById(int theId) {

		// dopiero tu używając .unwrap otrzymuje sesje hibernetową
		Session currentSession = entityManager.unwrap(Session.class);

		// odzyskaj/pobierz usera z bazy względem podanego id
		User theUser = currentSession.get(User.class, theId);

		return theUser;
	}

	@Override
	public User findByUserPhoneNumber(int thePhoneNumber) {
		// dopiero tu używając .unwrap otrzymuje sesje hibernetową
		Session currentSession = entityManager.unwrap(Session.class);

		// odzyskuje usera z bazy danych wg numeru telefonu
		Query<User> theQuery = currentSession.createQuery("from User where phoneNumber=:thePhoneNumber", User.class);
		theQuery.setParameter("thePhoneNumber", thePhoneNumber);

		User theUser = null;
		try {
			theUser = theQuery.getSingleResult();
		} catch (Exception e) {
			theUser = null;
		}

		return theUser;
	}

	@Override
	public User findByUserName(String theUserName) {
		// dopiero tu używając .unwrap otrzymuje sesje hibernetową
		Session currentSession = entityManager.unwrap(Session.class);

		// odzyskuje usera z bazy danych wg username
		Query<User> theQuery = currentSession.createQuery("from User where userName=:theUserName",
				User.class);
		theQuery.setParameter("theUserName", theUserName);

		User theUser = null;
		try {
			theUser = theQuery.getSingleResult();
		} catch (Exception e) {
			theUser = null;
		}

		return theUser;
	}

	@Override
	public User findByUserEmail(String theEmail) {

		// dopiero tu używając .unwrap otrzymuje sesje hibernetową
		Session currentSession = entityManager.unwrap(Session.class);

		// odzyskuje usera z bazy danych wg emaila
		Query<User> theQuery = currentSession.createQuery("from User where email=:theEmail", User.class);
		theQuery.setParameter("theEmail", theEmail);

		User theUser = null;
		try {
			theUser = theQuery.getSingleResult();
		} catch (Exception e) {
			theUser = null;
		}

		return theUser;
	}

}
