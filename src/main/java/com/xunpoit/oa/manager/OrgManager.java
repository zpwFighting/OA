package com.xunpoit.oa.manager;

import java.util.List;

import com.xunpoit.oa.entity.Org;
import com.xunpoit.oa.web.PageModel;

public interface OrgManager {
	
	//CRUD����
	public void  addOrg(Org org, int pid);
	
	//������ӽڵ㣬����ֱ��ɾ���û���
	public void delOrgById(int id);
	
	public void modifyOrg(Org org);
	
	public Org findOrgById(int id);
	//��ҳ����   ����pager-taglib�����   
	//         ��ҳ limit ?,?; offset ��ǰҳ��pageSize  ÿҳ����
	public PageModel<Org> findAll(int pid,int offset,int pageSize);

}
