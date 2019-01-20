package dev.mateusz.barber.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import dev.mateusz.barber.demo.dao.RoleDao;
import dev.mateusz.barber.demo.dao.UserDao;
import dev.mateusz.barber.demo.entity.Role;
import dev.mateusz.barber.demo.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

	@Autowired
	private UserService userService;

	@MockBean
	private UserDao userDao;

	@MockBean
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@MockBean
	private RoleDao roleDao;

	@Test
	public void testGetUserById() {
		List<Role> roles = new ArrayList<Role>();
		roles.add(new Role("ROLE_CUSTOMER"));
		User user = new User("testowy", "testowy", "Test", "Test", 999999999, "test@test.pl", roles);
		user.setIdUser(15);

		Mockito.when(userDao.getUserById(15)).thenReturn(user);

		Assert.assertEquals(user, userService.getUserById(15));
	}

	@Test
	public void testGetUsers() {
		List<Role> roles = new ArrayList<Role>();
		roles.add(new Role("ROLE_CUSTOMER"));
		User user = new User("testowy", "testowy", "Test", "Test", 999999999, "test@test.pl", roles);
		user.setIdUser(15);
		User user2 = new User("testowy2", "testowy2", "Test", "Test", 999999992, "test2@test.pl", roles);
		user.setIdUser(16);
		List<User> users = new ArrayList<User>();
		users.add(user);
		users.add(user2);

		Mockito.when(userDao.getUsers()).thenReturn(users);

		Assert.assertEquals(users, userService.getUsers());
	}

	@Test
	public void testSearchUsers() {
		List<Role> roles = new ArrayList<Role>();
		roles.add(new Role("ROLE_CUSTOMER"));
		User user = new User("testowy", "testowy", "Test", "Test", 999999999, "test@test.pl", roles);
		user.setIdUser(15);
		User user2 = new User("testowy2", "testowy2", "Test", "Test", 999999992, "test2@test.pl", roles);
		user.setIdUser(16);
		List<User> users = new ArrayList<User>();
		users.add(user);
		users.add(user2);

		Mockito.when(userDao.searchUsers("testowy")).thenReturn(users);
		

		Assert.assertEquals(users, userService.searchUsers("testowy"));
	}

	@Test
	public void testFindByUserName() {
		List<Role> roles = new ArrayList<Role>();
		roles.add(new Role("ROLE_CUSTOMER"));
		User user = new User("testowy", "testowy", "Test", "Test", 999999999, "test@test.pl", roles);
		user.setIdUser(15);

		Mockito.when(userDao.findByUserName("testowy", 15)).thenReturn(null);
		Mockito.when(userDao.findByUserName("testowy", 16)).thenReturn(user);

		Assert.assertNull(userService.findByUserName("testowy", 15));
		Assert.assertEquals(user, userService.findByUserName("testowy", 16));
	}

}
