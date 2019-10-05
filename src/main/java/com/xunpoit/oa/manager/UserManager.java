package com.xunpoit.oa.manager;

import java.util.List;

import com.xunpoit.oa.entity.Person;
import com.xunpoit.oa.entity.Role;
import com.xunpoit.oa.entity.User;
import com.xunpoit.oa.entity.UsersRoles;
import com.xunpoit.oa.web.PageModel;

public interface UserManager {
	
	public void addUser (User user,int personId);
	
	public void delUserById (int id);
	
	public void modityUser(User user);
	
	public User findUserById (int id);
	
	public void delUserByPerson(int personId);
	
	public PageModel<Person> findAll(int offset,int pageSize); 
	
	//给用户分配角色之前要列出用户已有的所有角色
	public List<UsersRoles> findRoleListByUser(int userId);
	
	//添加角色
	public void addUserRole (UsersRoles urs);
	
	//删除角色
	public void delUserRoleById (int ursId);
	
	//登录验证
	public User login(User user);
	
	
	
	

}
