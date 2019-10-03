package com.xunpoit.oa.manager;

import java.util.List;

import com.xunpoit.oa.entity.Org;
import com.xunpoit.oa.web.PageModel;

public interface OrgManager {
	
	//CRUD操作
	public void  addOrg(Org org, int pid);
	
	//如果有子节点，则不能直接删除该机构
	public void delOrgById(int id);
	
	public void modifyOrg(Org org);
	
	public Org findOrgById(int id);
	//分页方法   利用pager-taglib包完成   
	//         分页 limit ?,?; offset 当前页，pageSize  每页条数
	public PageModel<Org> findAll(int pid,int offset,int pageSize);

}
