package com.xunpoit.oa.dao;

import java.util.List;
import java.util.Map;

import com.xunpoit.oa.entity.Role;
import com.xunpoit.oa.entity.UsersRoles;

public interface UsersRolesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UsersRoles record);

    int insertSelective(UsersRoles record);

    UsersRoles selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UsersRoles record);

    int updateByPrimaryKey(UsersRoles record);

	List<Role> selectRolesByUser(int userId);

	List<UsersRoles> selectUsersRolesByUser(int userId);
    //��ѯ�û�ӵ�����н�ɫ��desc�������0����  �������1����
	List<Role> selectRoleListByUser(Map<String, Integer> map);
}