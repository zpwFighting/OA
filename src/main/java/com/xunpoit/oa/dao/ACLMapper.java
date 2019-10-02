package com.xunpoit.oa.dao;

import com.xunpoit.oa.entity.ACL;

public interface ACLMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ACL record);

    int insertSelective(ACL record);

    ACL selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ACL record);

    int updateByPrimaryKey(ACL record);
}