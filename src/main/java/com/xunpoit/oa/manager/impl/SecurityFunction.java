package com.xunpoit.oa.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xunpoit.oa.manager.ACLManager;

@Service
public class SecurityFunction {
	
	private static ACLManager aclManager;
	
	public  ACLManager getAclManager() {
		return aclManager;
	}
	
	@Autowired
	public  void setAclManager(ACLManager aclManager) {
		SecurityFunction.aclManager = aclManager;
	}

	//即时验证方法
	
	public static boolean getPermission(int userId,String sn ,int permission) {
		//aclManager.getPermission(userId, sn, permission)
		if(aclManager==null) System.out.println("asdfsdfsafa");
		return aclManager.getPermission(userId, sn, permission);
	}

}
