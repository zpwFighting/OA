package com.xunpoit.oa.manager;

import java.util.List;

import com.xunpoit.oa.entity.Org;

public interface OrgManager {
	
	//CRUD����
	public void  addOrg(Org org, int pid);
	
	//������ӽڵ㣬����ֱ��ɾ���û���
	public void delOrgById(int id);
	
	public void modifyOrg(Org org);
	
	public Org findOrgById(int id);
	
	public List<Org> findAll(int pid);

}
