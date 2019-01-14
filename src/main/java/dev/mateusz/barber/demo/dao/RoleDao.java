package dev.mateusz.barber.demo.dao;

import dev.mateusz.barber.demo.entity.Role;

public interface RoleDao {
	
	public Role findRoleByName(String theRoleName);

}
