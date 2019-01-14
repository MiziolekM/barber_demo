package dev.mateusz.barber.demo.service;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import dev.mateusz.barber.demo.dao.RoleDao;
import dev.mateusz.barber.demo.dao.UserDao;
import dev.mateusz.barber.demo.dto.CrmUser;
import dev.mateusz.barber.demo.entity.Role;
import dev.mateusz.barber.demo.entity.User;

@Service
public class UserServiceImpl implements UserService {
	
	// wstrzykuje oba dao
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;
	
	// wstrzykuje kodowanie
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	@Transactional
	public User findByUserName(String theUserName, int theIdUser) {
		// sprawdzam czy już taki użytkownik może istnieje - z taką nazwą użytkownika
		return userDao.findByUserName(theUserName, theIdUser);
	}

	@Override
	@Transactional
	public void saveUser(CrmUser theCrmUser) {
		
		User user = new User();
		
		// wypełniam pusty obiekt
		user.setIdUser(0);
		user.setUserName(theCrmUser.getUserName());
		user.setPassword(bCryptPasswordEncoder.encode(theCrmUser.getPassword()));
		user.setFirstName(theCrmUser.getFirstName());
		user.setLastName(theCrmUser.getLastName());
		user.setEmail(theCrmUser.getEmail());
		user.setPhoneNumber(theCrmUser.getPhoneNumber());

		// dodaje domyślną rolę customer
		Role role = roleDao.findRoleByName("ROLE_CUSTOMER");
		user.setRoles(Arrays.asList(role));
		
		 // zapisuje usera
		userDao.saveUser(user);
	}

	@Override
	@Transactional
	public User findByUserPhoneNumber(int thePhoneNumber, int theIdUser) {
		// sprawdzam czy już taki użytkownik może istnieje - z takim numerem telefonu
		return userDao.findByUserPhoneNumber(thePhoneNumber, theIdUser);
	}

	@Override
	@Transactional
	public User findByUserEmail(String theEmail, int theIdUser) {
		// sprawdzam czy już taki użytkownik może istnieje - z takim emailem
		return userDao.findByUserEmail(theEmail, theIdUser);
	}

	@Override
	@Transactional
	public List<User> getUsers() {
		
		return userDao.getUsers();
	}

	@Override
	@Transactional
	public void deleteUser(int theId) {
		
		userDao.deleteUser(theId);
	}

	@Override
	@Transactional
	public List<User> searchUsers(String theSearchName) {
		
		return userDao.searchUsers(theSearchName);
	}

	@Override
	@Transactional
	public User getUserById(int theId) {
		
		return userDao.getUserById(theId);
	}

	@Override
	@Transactional
	public void updateUser(CrmUser theCrmUser) {
		
		User user = new User();
		
		// wypełniam pusty obiekt
		user.setIdUser(theCrmUser.getIdUser());
		user.setUserName(theCrmUser.getUserName());
		user.setPassword(theCrmUser.getPassword());
		user.setFirstName(theCrmUser.getFirstName());
		user.setLastName(theCrmUser.getLastName());
		user.setEmail(theCrmUser.getEmail());
		user.setPhoneNumber(theCrmUser.getPhoneNumber());

		Role role = roleDao.findRoleByName("ROLE_CUSTOMER");
		user.setRoles(Arrays.asList(role));
		Logger logger = Logger.getLogger(getClass().getName());
		logger.info("---123-->"+user.getRoles());
		userDao.saveUser(user);
	}

	@Override
	@Transactional
	public User findByUserPhoneNumber(int thePhoneNumber) {
		return userDao.findByUserPhoneNumber(thePhoneNumber);
	}

	@Override
	@Transactional
	public User findByUserName(String theUserName) {
		// TODO Auto-generated method stub
		return userDao.findByUserName(theUserName);
	}

	@Override
	@Transactional
	public User findByUserEmail(String theEmail) {
		// TODO Auto-generated method stub
		return userDao.findByUserEmail(theEmail);
	}
	
}
