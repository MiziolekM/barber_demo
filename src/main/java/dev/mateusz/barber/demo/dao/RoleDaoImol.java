package dev.mateusz.barber.demo.dao;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dev.mateusz.barber.demo.entity.Role;

@Repository
public class RoleDaoImol implements RoleDao {
	
	// wykorzystanie EntityManager
	private EntityManager entityManager;

	// wstrzyknięcie dopiero tu
	@Autowired
	public RoleDaoImol(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public Role findRoleByName(String theRoleName) {
		
		// dopiero tu używając .unwrap otrzymuje sesje hibernetową
		Session currentSession = entityManager.unwrap(Session.class);
		
		// odzyskuje role z bazy danych wg nazwy roli
		Query<Role> theQuery = currentSession.createQuery("from Role where role=:theRoleName", Role.class);
		theQuery.setParameter("theRoleName", theRoleName);
		
		Role theRole = null;
		
		try {
			theRole = theQuery.getSingleResult();
		} catch (Exception e) {
			theRole = null;
		}
		
		return theRole;
	}





}
