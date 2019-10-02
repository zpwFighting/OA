package com.xunpoit.oa.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xunpoit.oa.dao.OrgMapper;
import com.xunpoit.oa.entity.Org;
import com.xunpoit.oa.manager.OrgManager;

@Service("orgManager")
public class OrgManagerImpl implements OrgManager {

	@Autowired
	private OrgMapper orgMapper;

	@Override
	public void addOrg(Org org, int pid) {
		if (pid > 0) {
			Org parent = orgMapper.selectByPrimaryKey(pid);
			org.setParent(parent);
		}
		orgMapper.insertSelective(org);

	}

	@Override
	public void delOrgById(int id) {
		orgMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void modifyOrg(Org org) {
		orgMapper.updateByPrimaryKeySelective(org);
	}

	@Override
	public Org findOrgById(int id) {
		return orgMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Org> findAll(int pid) {
		return orgMapper.findAllByParent(pid);
	}

}
