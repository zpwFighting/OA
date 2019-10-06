package com.xunpoit.oa.dao;

import java.util.List;
import java.util.Map;

import com.xunpoit.oa.entity.Module;
public interface ModuleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Module record);

    int insertSelective(Module record);

    Module selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Module record);

    int updateByPrimaryKey(Module record);

	Long findCount(int pid);

	List<Module> findAllByParent(Map<String ,Integer> map);

	List<Module> findALLModuleListByKey(List<Integer> idList);

	int findModuleIdBySn(String sn);


	
}