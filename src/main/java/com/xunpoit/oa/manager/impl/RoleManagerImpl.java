package com.xunpoit.oa.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xunpoit.oa.dao.RoleMapper;
import com.xunpoit.oa.entity.Role;
import com.xunpoit.oa.manager.RoleManager;

@Service
public class RoleManagerImpl implements RoleManager {
	
	@Autowired
	RoleMapper roleMapper;

	@Override
	public void addRole(Role role) {
		roleMapper.insertSelective(role);
	}

	@Override
	public void delRoleById(int id) {
		roleMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void modifyRole(Role role) {
		roleMapper.updateByPrimaryKeySelective(role);
	}

	@Override
	public Role findRoleById(int id) {
		// TODO Auto-generated method stub
		return roleMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Role> findAll() {
		return roleMapper.findAll();
	}

}
