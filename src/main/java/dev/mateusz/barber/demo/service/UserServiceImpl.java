package dev.mateusz.barber.demo.service;

import java.util.Arrays;
import java.util.List;

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
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	@Transactional
	public User findByUserName(String theUserName, int theIdUser) {
		return userDao.findByUserName(theUserName, theIdUser);
	}

	@Override
	@Transactional
	public void saveUser(CrmUser theCrmUser) {
		
		User user = new User();

		user.setIdUser(0);
		user.setUserName(theCrmUser.getUserName());
		user.setPassword(bCryptPasswordEncoder.encode(theCrmUser.getPassword()));
		user.setFirstName(theCrmUser.getFirstName());
		user.setLastName(theCrmUser.getLastName());
		user.setEmail(theCrmUser.getEmail());
		user.setPhoneNumber(theCrmUser.getPhoneNumber());

		Role role = roleDao.findRoleByName("ROLE_CUSTOMER");
		user.setRoles(Arrays.asList(role));
		
		userDao.saveUser(user);
	}

	@Override
	@Transactional
	public User findByUserPhoneNumber(int thePhoneNumber, int theIdUser) {
		return userDao.findByUserPhoneNumber(thePhoneNumber, theIdUser);
	}

	@Override
	@Transactional
	public User findByUserEmail(String theEmail, int theIdUser) {
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
		
		user.setIdUser(theCrmUser.getIdUser());
		user.setUserName(theCrmUser.getUserName());
		user.setPassword(theCrmUser.getPassword());
		user.setFirstName(theCrmUser.getFirstName());
		user.setLastName(theCrmUser.getLastName());
		user.setEmail(theCrmUser.getEmail());
		user.setPhoneNumber(theCrmUser.getPhoneNumber());

		Role role = roleDao.findRoleByName("ROLE_CUSTOMER");
		user.setRoles(Arrays.asList(role));
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
		return userDao.findByUserName(theUserName);
	}

	@Override
	@Transactional
	public User findByUserEmail(String theEmail) {
		return userDao.findByUserEmail(theEmail);
	}
	
}
