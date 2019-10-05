package com.xunpoit.oa.manager;

import java.security.acl.Acl;
import java.util.List;

import com.xunpoit.oa.entity.Module;

/**
 * 权限相关类
 * @author Administrator
 *
 */
public interface ACLManager {
	
	public void addOrUpdateAcl(String mainType,int mainid, int moduleId,int permission,boolean yes);
	
	public void delAcl(String mainType,int mainid, int moduleId);
	//yes 为true代表不继承（0），false代表继承（1）
	public void updateExtendState(int userId,int moduleId,boolean yes);
	//初始化授权页面
	//返回类型为一个二维数组
	//moduleId C R U D Ext
	//1        1 2 4 8  0
	//2        1 0 0 0  0
	//.....
	public List findAllAclByMainTypeMainId(String mainType,int mainid); 
	
	
	//登陆后查询出该用户具有读取权限的模块
	public List<Module> findAllModuleByUserId(int userId);
	
	//即时验证  当一个用户登录后验证该用户对某一模块的某一操作是否允许
	public boolean getPermission (int userId,int moduleId,int permission);

}
