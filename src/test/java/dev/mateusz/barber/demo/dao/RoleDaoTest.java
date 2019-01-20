package dev.mateusz.barber.demo.dao;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dev.mateusz.barber.demo.entity.Role;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RoleDaoTest {
	
	@Autowired
	public RoleDao roleDao;
	
	@Test
    @Transactional
    @Rollback(true)
    public void testFindRoleByName()
    {
		String customer = "ROLE_CUSTOMER";
		Role roleExpect = new Role(customer);
		roleExpect.setIdRole(1);
		
		Role role = roleDao.findRoleByName(customer);
		
		Assert.assertEquals(roleExpect, role);
    }

}
