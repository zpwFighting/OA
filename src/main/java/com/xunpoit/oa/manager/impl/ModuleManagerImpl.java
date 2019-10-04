package com.xunpoit.oa.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xunpoit.oa.dao.ModuleMapper;
import com.xunpoit.oa.entity.Module;
import com.xunpoit.oa.manager.ModuleManager;
import com.xunpoit.oa.web.PageModel;
@Service
public class ModuleManagerImpl implements ModuleManager {
	
	@Autowired
	ModuleMapper moduleMapper;

	@Override
	public void addModule(Module module, int pid) {
		if(pid>0) {
			Module parent = moduleMapper.selectByPrimaryKey(pid);
			module.setParent(parent);
		}
		moduleMapper.insertSelective(module);
	}

	@Override
	public void delModuleById(int id) {
		moduleMapper.deleteByPrimaryKey(id);

	}

	@Override
	public void modityModule(Module moduel) {
		moduleMapper.updateByPrimaryKeySelective(moduel);

	}

	@Override
	public Module findModuleById(int id) {
		return moduleMapper.selectByPrimaryKey(id);
	}

	@Override
	public PageModel<Module> findAll(int pid, int offset, int pageSize) {
		PageModel<Module> pm = new PageModel<Module>();
		//查总条数
		long count = moduleMapper.findCount(pid);
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("pid",pid);
		map.put("offset",offset);
		map.put("pageSize", pageSize);
		//查数据
		List<Module> dataList = moduleMapper.findAllByParent(map);
		
		pm.setDataList(dataList);
		pm.setPageSize(pageSize);
		pm.setItems((int)count);
		
		return pm;
	}

}
