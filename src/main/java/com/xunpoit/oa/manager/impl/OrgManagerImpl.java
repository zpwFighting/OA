package com.xunpoit.oa.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xunpoit.oa.dao.OrgMapper;
import com.xunpoit.oa.entity.Org;
import com.xunpoit.oa.manager.OrgManager;
import com.xunpoit.oa.web.PageModel;

@Service("orgManager")
public class OrgManagerImpl implements OrgManager {

	@Autowired
	private OrgMapper orgMapper;

	@Override
	public void addOrg(Org org, int pid) {
		//添加   但是没有设置机构编号  
		//    机构编号： 1.父机构+"_"+id;  2.当没有父机构时直接为id
		Org parent = null;
		if (pid > 0) {
		    parent = orgMapper.selectByPrimaryKey(pid);
			org.setParent(parent);
		}
		orgMapper.insertSelective(org);
		//设置机构编号  初始为自己id
		String sn = ""+org.getId();
		//当有父机构时  
		if(pid>0) {
			sn = parent.getId()+"_"+org.getId();
		}
		org.setSn(sn);
		//进行更新
		orgMapper.updateByPrimaryKeySelective(org);
		//事物 spring 声明式事务管理  
		/**
		 * 声明事物要四步：
		 *   1.
		 */

	}

	@Override
	public void delOrgById(int id) {
		//删除时先判断是否有子机构
		Org org = orgMapper.selectByPrimaryKey(id);
		if(org.getChildList().size()>0) {
			throw new RuntimeException("有子机构，不能删除。删除失败！！！");
		}
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
	public PageModel<Org> findAll(int pid,int offset,int pageSize) {
		//如何给mybatis传多个值  --》 Map<key,value>
		Map<String,Integer> paramMap = new HashMap<String,Integer>();
		paramMap.put("pid", pid);
		paramMap.put("offset", offset);
		paramMap.put("pageSize", pageSize);
		List<Org> orgList = orgMapper.findAllByParent(paramMap);
		PageModel<Org> pageModel = new PageModel<Org>();
		pageModel.setDataList(orgList);
		pageModel.setPageSize(pageSize);
		//设置总条数
		long items = orgMapper.selectCount(pid);
		pageModel.setItems((int)items);
		return pageModel;
	}

}
