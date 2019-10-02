package com.xunpoit.oa.manager;

import java.util.List;

import com.xunpoit.oa.entity.Org;

public interface OrgManager {
	
	//CRUD操作
	public void  addOrg(Org org, int pid);
	
	//如果有子节点，则不能直接删除该机构
	public void delOrgById(int id);
	
	public void modifyOrg(Org org);
	
	public Org findOrgById(int id);
	
	public List<Org> findAll(int pid);

}
