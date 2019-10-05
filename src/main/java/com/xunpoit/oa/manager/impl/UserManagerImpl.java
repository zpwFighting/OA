package com.xunpoit.oa.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xunpoit.oa.dao.PersonMapper;
import com.xunpoit.oa.dao.UserMapper;
import com.xunpoit.oa.dao.UsersRolesMapper;
import com.xunpoit.oa.entity.Person;
import com.xunpoit.oa.entity.Role;
import com.xunpoit.oa.entity.User;
import com.xunpoit.oa.entity.UsersRoles;
import com.xunpoit.oa.manager.UserManager;
import com.xunpoit.oa.web.PageModel;

@Service
public class UserManagerImpl implements UserManager {
	
	@Autowired
	UserMapper userMapper;
	@Autowired
	PersonMapper personMapper;
	@Autowired
	private UsersRolesMapper ursMapper;

	@Override
	public void addUser(User user, int personId) {
		Person person = personMapper.selectByPrimaryKey(personId);
		user.setPerson(person);
		userMapper.insertSelective(user);
	}

	@Override
	public void delUserById(int id) {
		userMapper.deleteByPrimaryKey(id);

	}

	@Override
	public void modityUser(User user) {
		userMapper.updateByPrimaryKeySelective(user);

	}

	@Override
	public User findUserById(int id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public PageModel<Person> findAll(int offset, int pageSize) {
		PageModel<Person> pageModel = new PageModel<Person>();
		Map<String ,Integer> personMap = new HashMap<String ,Integer>();
		personMap.put("offset", offset);
		personMap.put("pageSize", pageSize);
		List<Person> dataList = personMapper.findAll(personMap);
		//查询总条数
		long items = personMapper.findItems();
		pageModel.setDataList(dataList);
		pageModel.setItems((int)items);
		pageModel.setPageSize(pageSize);
		return pageModel;
	}

	@Override
	public List<UsersRoles> findRoleListByUser(int userId) {
		List<UsersRoles> list = ursMapper.selectUsersRolesByUser(userId);
		return list;
	}

	@Override
	public void addUserRole(UsersRoles urs) {
		ursMapper.insertSelective(urs);
	}

	@Override
	public void delUserRoleById(int ursId) {
		ursMapper.deleteByPrimaryKey(ursId);
	}

	@Override
	public User login(User user) {
		return userMapper.login(user);
	}

	@Override
	public void delUserByPerson(int personId) {
		userMapper.deleteByPersonId(personId);
	}

}
