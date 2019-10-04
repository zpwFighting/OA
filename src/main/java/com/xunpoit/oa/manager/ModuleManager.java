package com.xunpoit.oa.manager;

import com.xunpoit.oa.entity.Module;
import com.xunpoit.oa.web.PageModel;

public interface ModuleManager {
	
	public void addModule (Module module,int pid);
	
	public void delModuleById (int id);
	
	public void modityModule (Module moduel);
	
	public Module findModuleById (int id);
	
	public PageModel<Module> findAll (int pid,int offset,int pageSize);

}
