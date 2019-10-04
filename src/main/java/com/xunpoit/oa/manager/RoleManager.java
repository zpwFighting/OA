package com.xunpoit.oa.manager;

import java.util.List;

import com.xunpoit.oa.entity.Role;

public interface RoleManager {
	
	public void addRole (Role role);
	
	public void delRoleById (int id);
	
	public void modifyRole(Role role);
	
	public Role findRoleById (int id);
	
	public List<Role> findAll ();

}
