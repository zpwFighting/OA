package com.xunpoit.oa.dao;

import java.util.List;
import java.util.Map;

import com.xunpoit.oa.entity.ACL;
import com.xunpoit.oa.entity.ACLCustom;

public interface ACLMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ACL record);

    int insertSelective(ACL record);

    ACL selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ACL record);

    int updateByPrimaryKey(ACL record);

	ACL selectACLByMain(Map<String, Object> map);

	List<ACLCustom> findAllAclByMainTypeMainId(Map<String, Object> map);

	List<ACL> findAllAclListByMainTypeMainId(Map<String, Object> map);
}