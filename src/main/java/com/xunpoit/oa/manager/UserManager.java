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
	
	//���û������ɫ֮ǰҪ�г��û����е����н�ɫ
	public List<UsersRoles> findRoleListByUser(int userId);
	
	//��ӽ�ɫ
	public void addUserRole (UsersRoles urs);
	
	//ɾ����ɫ
	public void delUserRoleById (int ursId);
	
	//��¼��֤
	public User login(User user);
	
	
	
	

}
