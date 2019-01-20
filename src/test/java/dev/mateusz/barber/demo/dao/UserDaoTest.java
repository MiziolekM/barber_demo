package dev.mateusz.barber.demo.dao;


import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dev.mateusz.barber.demo.entity.Role;
import dev.mateusz.barber.demo.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserDaoTest {
	
	User admin = null;
	List<Role> roles = new ArrayList<Role>();
	List<Role> rolesOnlyCustomer = new ArrayList<Role>();

	@Autowired
	private UserDao userDao;
	
	@Before
	public void prepare() {
		
		Role customer_role = new Role("ROLE_CUSTOMER");
		customer_role.setIdRole(1);
		rolesOnlyCustomer.add(customer_role);
		
		Role moderator_role = new Role("ROLE_MODERATOR");
		moderator_role.setIdRole(2);
		
		Role admin_role = new Role("ROLE_ADMIN");
		admin_role.setIdRole(3);
		
		roles.add(customer_role);
		roles.add(moderator_role);
		roles.add(admin_role);
		
		admin = new User("admin", "$2a$04$3fowOcmQFkDXLkexyP/L..v51xYe1yvhkwdzOEgwDOp.YMo6GD0Cm", "Admin", "Adminowy", 666666666, "admin@admin.pl", roles);
		admin.setIdUser(1);
	}
	
	@Test
    @Transactional
    @Rollback(true)
    public void testFindByUserName() {
		
		String theUserName = "admin";
		
		User user = userDao.findByUserName(theUserName);
		
		Assert.assertEquals(admin.getIdUser(), user.getIdUser());
		Assert.assertEquals(admin.getEmail(), user.getEmail());
		Assert.assertEquals(admin.getRoles().get(0).getRole(), user.getRoles().get(0).getRole());
		Assert.assertEquals(admin.getRoles().get(1).getRole(), user.getRoles().get(1).getRole());		
		Assert.assertEquals(admin.getRoles().get(2).getRole(), user.getRoles().get(2).getRole());		
		Assert.assertEquals(admin.getLastName(), user.getLastName());
		Assert.assertEquals(admin.getPassword(), user.getPassword());
		Assert.assertEquals(admin.getUserName(), user.getUserName());
		Assert.assertEquals(admin.getPhoneNumber(), user.getPhoneNumber());
	}
	
	@Test
    @Transactional
    @Rollback(true)
    public void testFindByUserNameWithId() {
		
		String theUserName = "admin";
		
		User user = userDao.findByUserName(theUserName, 1);
		User user2 = userDao.findByUserName(theUserName, 2);
		
		Assert.assertNull(user);
		
		Assert.assertEquals(admin.getIdUser(), user2.getIdUser());
		Assert.assertEquals(admin.getUserName(), user2.getUserName());
	}
	
	@Test
    @Transactional
    @Rollback(true)
    public void testFindByUserPhoneNumber() {
		
		int theUserPhoneNumber = 666666666;
		
		User user = userDao.findByUserPhoneNumber(theUserPhoneNumber);
		
		Assert.assertEquals(admin.getIdUser(), user.getIdUser());
		Assert.assertEquals(admin.getEmail(), user.getEmail());
		Assert.assertEquals(admin.getRoles().get(0).getRole(), user.getRoles().get(0).getRole());
		Assert.assertEquals(admin.getRoles().get(1).getRole(), user.getRoles().get(1).getRole());		
		Assert.assertEquals(admin.getRoles().get(2).getRole(), user.getRoles().get(2).getRole());		
		Assert.assertEquals(admin.getLastName(), user.getLastName());
		Assert.assertEquals(admin.getPassword(), user.getPassword());
		Assert.assertEquals(admin.getUserName(), user.getUserName());
		Assert.assertEquals(admin.getPhoneNumber(), user.getPhoneNumber());
	}
	
	@Test
    @Transactional
    @Rollback(true)
    public void testFindByUserPhoneNumberWithId() {
		
		int theUserPhoneNumber = 666666666;
		
		User user = userDao.findByUserPhoneNumber(theUserPhoneNumber, 1);
		User user2 = userDao.findByUserPhoneNumber(theUserPhoneNumber, 2);
		
		Assert.assertNull(user);
		
		Assert.assertEquals(admin.getIdUser(), user2.getIdUser());
		Assert.assertEquals(admin.getUserName(), user2.getUserName());
	}
	
	@Test
    @Transactional
    @Rollback(true)
    public void testFindByUserEmail() {
		
		String theUserEmail = "admin@admin.pl";
		
		User user = userDao.findByUserEmail(theUserEmail);
		
		Assert.assertEquals(admin.getIdUser(), user.getIdUser());
		Assert.assertEquals(admin.getEmail(), user.getEmail());
		Assert.assertEquals(admin.getRoles().get(0).getRole(), user.getRoles().get(0).getRole());
		Assert.assertEquals(admin.getRoles().get(1).getRole(), user.getRoles().get(1).getRole());		
		Assert.assertEquals(admin.getRoles().get(2).getRole(), user.getRoles().get(2).getRole());		
		Assert.assertEquals(admin.getLastName(), user.getLastName());
		Assert.assertEquals(admin.getPassword(), user.getPassword());
		Assert.assertEquals(admin.getUserName(), user.getUserName());
		Assert.assertEquals(admin.getPhoneNumber(), user.getPhoneNumber());
	}
	
	@Test
    @Transactional
    @Rollback(true)
    public void testFindByUserEmailWithId() {
		
		String theUserEmail = "admin@admin.pl";
		
		User user = userDao.findByUserEmail(theUserEmail, 1);
		User user2 = userDao.findByUserEmail(theUserEmail, 2);
		
		Assert.assertNull(user);
		
		Assert.assertEquals(admin.getIdUser(), user2.getIdUser());
		Assert.assertEquals(admin.getUserName(), user2.getUserName());
	}
	
	@Test
    @Transactional
    @Rollback(true)
    public void testGetUserById() {
		
		int tempId = 1;
		
		User user = userDao.getUserById(tempId);
		
		Assert.assertEquals(admin.getIdUser(), user.getIdUser());
		Assert.assertEquals(admin.getEmail(), user.getEmail());
		Assert.assertEquals(admin.getRoles().get(0).getRole(), user.getRoles().get(0).getRole());
		Assert.assertEquals(admin.getRoles().get(1).getRole(), user.getRoles().get(1).getRole());		
		Assert.assertEquals(admin.getRoles().get(2).getRole(), user.getRoles().get(2).getRole());		
		Assert.assertEquals(admin.getLastName(), user.getLastName());
		Assert.assertEquals(admin.getPassword(), user.getPassword());
		Assert.assertEquals(admin.getUserName(), user.getUserName());
		Assert.assertEquals(admin.getPhoneNumber(), user.getPhoneNumber());
	}
	
	@Test
    @Transactional
    @Rollback(true)
    public void testSaveUser() {
		
		User user = new User("janusz", "janusz123", "Admin", "Adminowy", 666666669, "janusz@admin.pl", rolesOnlyCustomer);
		userDao.saveUser(user);
		
		User userSave = userDao.findByUserName("janusz");
		
		Assert.assertEquals(userSave.getIdUser(), user.getIdUser());
		Assert.assertEquals(userSave.getEmail(), user.getEmail());
		Assert.assertEquals(userSave.getRoles().get(0).getRole(), user.getRoles().get(0).getRole());	
		Assert.assertEquals(userSave.getLastName(), user.getLastName());
		Assert.assertEquals(userSave.getPassword(), user.getPassword());
		Assert.assertEquals(userSave.getUserName(), user.getUserName());
		Assert.assertEquals(userSave.getPhoneNumber(), user.getPhoneNumber());
	}
	
	@Test
    @Transactional
    @Rollback(true)
    public void testGetUsers() {
		
		List<User> users = userDao.getUsers();
		
		Assert.assertNotNull(users);
	}
	
	@Test
    @Transactional
    @Rollback(true)
    public void testDeleteUser() {
		
		User user = new User("janusz", "janusz123", "Admin", "Adminowy", 666666669, "janusz@admin.pl", rolesOnlyCustomer);
		userDao.saveUser(user);
		
		User usersBefore = userDao.findByUserName("janusz");
		
		userDao.deleteUser(user.getIdUser());
		
		User usersAfter = userDao.findByUserName("janusz");
		
		Assert.assertNotNull(usersBefore);
		Assert.assertNull(usersAfter);
	}
	
	@Test
    @Transactional
    @Rollback(true)
    public void testSearchUsers() {
		
		List<User> users = userDao.searchUsers("jan");
		
		Assert.assertNotNull(users);
	}
}
