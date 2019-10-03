package com.xunpoit.oa.dao;

import java.util.List;
import java.util.Map;

import com.xunpoit.oa.entity.Org;
import com.xunpoit.oa.web.PageModel;

public interface OrgMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Org record);

    int insertSelective(Org record);

    Org selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Org record);

    int updateByPrimaryKey(Org record);
    //三个参数  pid，offset，pageSize
	List<Org> findAllByParent(Map<String,Integer> paramMap);
	//查询总条数 根据  pid
	long selectCount(int pid);
}