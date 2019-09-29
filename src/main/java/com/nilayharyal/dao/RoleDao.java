package com.nilayharyal.dao;

import com.nilayharyal.entity.Role;

public interface RoleDao {

	public Role findRoleByName(String theRoleName);
	
}
