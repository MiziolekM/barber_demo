package dev.mateusz.barber.demo.service;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import dev.mateusz.barber.demo.dao.RoleDao;
import dev.mateusz.barber.demo.dao.UserDao;
import dev.mateusz.barber.demo.dto.CrmUser;
import dev.mateusz.barber.demo.entity.Role;
import dev.mateusz.barber.demo.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceIntegTest {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Test
	@Transactional
	@Rollback(true)
	public void testSaveUser() {
		
		CrmUser theCrmUser = new CrmUser();
		theCrmUser.setUserName("testowy");
		theCrmUser.setFirstName("Test");
		theCrmUser.setLastName("Test");
		theCrmUser.setEmail("test@test.pl");
		theCrmUser.setPassword("test123");
		theCrmUser.setMatchingPassword("test123");
		
		User user = new User();

		user.setUserName(theCrmUser.getUserName());
		user.setFirstName(theCrmUser.getFirstName());
		user.setLastName(theCrmUser.getLastName());
		user.setPassword(bCryptPasswordEncoder.encode(theCrmUser.getPassword()));
		user.setEmail(theCrmUser.getEmail());
		user.setPhoneNumber(theCrmUser.getPhoneNumber());

		Role roleCustomer = new Role("ROLE_CUSTOMER");
		roleCustomer.setIdRole(1);
		
		Role role = roleDao.findRoleByName("ROLE_CUSTOMER");
		user.setRoles(Arrays.asList(role));
		
		List<User> usersBeforeSave =  userService.getUsers();
		
		userService.saveUser(theCrmUser);
		
		List<User> usersAfterSave =  userService.getUsers();
		
		Assert.assertNotEquals(usersBeforeSave, usersAfterSave);
		Assert.assertEquals(user.getFirstName(), usersAfterSave.get(usersAfterSave.size()-1).getFirstName());
		Assert.assertEquals(user.getLastName(), usersAfterSave.get(usersAfterSave.size()-1).getLastName());
		Assert.assertEquals(user.getEmail(), usersAfterSave.get(usersAfterSave.size()-1).getEmail());
		Assert.assertEquals(user.getPhoneNumber(), usersAfterSave.get(usersAfterSave.size()-1).getPhoneNumber());
		Assert.assertEquals(user.getRoles().get(0).getRole(), usersAfterSave.get(usersAfterSave.size()-1).getRoles().get(0).getRole());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdateUser() {
		
		CrmUser theCrmUser = new CrmUser();
		theCrmUser.setUserName("testowy");
		theCrmUser.setFirstName("Test");
		theCrmUser.setLastName("Test");
		theCrmUser.setEmail("test@test.pl");
		theCrmUser.setPassword("test123");
		theCrmUser.setMatchingPassword("test123");
		
		userService.saveUser(theCrmUser);
		
		User findUser = userDao.findByUserName(theCrmUser.getUserName());
		
		theCrmUser.setIdUser(findUser.getIdUser());
		theCrmUser.setEmail("zmieniony@test.pl");
		theCrmUser.setUserName("testowy2");
		theCrmUser.setPassword(findUser.getPassword());
		theCrmUser.setPhoneNumber(987789987);

		userService.saveUser(theCrmUser);
		
		List<User> usersAfterSave =  userDao.getUsers();
		
		Assert.assertEquals("testowy2", usersAfterSave.get(usersAfterSave.size()-1).getUserName());
		Assert.assertEquals(findUser.getFirstName(), usersAfterSave.get(usersAfterSave.size()-1).getFirstName());
		Assert.assertEquals(findUser.getLastName(), usersAfterSave.get(usersAfterSave.size()-1).getLastName());
		Assert.assertEquals("zmieniony@test.pl", usersAfterSave.get(usersAfterSave.size()-1).getEmail());
		Assert.assertEquals(987789987, usersAfterSave.get(usersAfterSave.size()-1).getPhoneNumber());
		Assert.assertEquals(findUser.getRoles().get(0).getRole(), usersAfterSave.get(usersAfterSave.size()-1).getRoles().get(0).getRole());
	}

}
