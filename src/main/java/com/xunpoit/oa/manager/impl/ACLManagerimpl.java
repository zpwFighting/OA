package com.xunpoit.oa.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xunpoit.oa.dao.ACLMapper;
import com.xunpoit.oa.dao.ModuleMapper;
import com.xunpoit.oa.dao.UsersRolesMapper;
import com.xunpoit.oa.entity.ACL;
import com.xunpoit.oa.entity.ACLCustom;
import com.xunpoit.oa.entity.Module;
import com.xunpoit.oa.entity.Role;
import com.xunpoit.oa.manager.ACLManager;

@Service
public class ACLManagerimpl implements ACLManager {

	@Autowired
	private ACLMapper aclMapper;
	@Autowired
	private UsersRolesMapper ursMapper;
	@Autowired
	private ModuleMapper moduleMapper;
    
	@Override
	public void addOrUpdateAcl(String mainType, int mainId, int moduleId, int permission, boolean yes) {
		// 查询是够存在这个ACL,如果存在更新，如果不存在就插入，
		ACL acl = this.findACL(mainType, mainId, moduleId);
		if(acl!=null) {
			//存在,更新
			//改变acl_state
			acl.setPermission(permission, yes);
			//跟新数据库
			aclMapper.updateByPrimaryKeySelective(acl);
			return ;
		}
		//不存在，添加
		acl = new ACL();
		acl.setMainId(mainId);
		acl.setMainType(mainType);
		acl.setModuleId(moduleId);
		//设置acl_state
		acl.setPermission(permission, yes);
		aclMapper.insertSelective(acl);

	}
	private ACL findACL(String mainType, int mainId, int moduleId) {
		Map<String ,Object> map = new HashMap<String,Object>();
		map.put("mainType",mainType);
		map.put("mainId",mainId);
		map.put("moduleId",moduleId);
		ACL acl = aclMapper.selectACLByMain(map);
		return acl;
	}

	@Override
	public void delAcl(String mainType, int mainId, int moduleId) {
		ACL acl = this.findACL(mainType, mainId, moduleId);
        if(acl!=null) {
        	aclMapper.deleteByPrimaryKey(acl.getId());
        }
	}

	@Override
	public void updateExtendState(int userId, int moduleId, boolean yes) {
		ACL acl = this.findACL(ACL.TYPE_USER, userId, moduleId);
        if(acl!=null) {
        	acl.setExtendState(yes?0:1);
        	aclMapper.updateByPrimaryKeySelective(acl);
        }
	}

	//初始化授权页面
	/**
	 * mysql>  select module_id,acl_state&1,acl_state&2,acl_state&4,acl_state&8,extend_state 
	 *         from t_acl 
	 *         where main_type='Role' and main_id=1;
	 */
	@Override
	public List findAllAclByMainTypeMainId(String mainType, int mainId) {
		//打开权限管理页面时  先通过这个方法查询数据库   得到某某的所有权限    进行显示
		//返回格式是一个二维数组
		Map<String ,Object> map = new HashMap<>();
		map.put("mainType", mainType);
		map.put("mainId", mainId);
		List<ACLCustom> aclCustomList = aclMapper.findAllAclByMainTypeMainId(map);
		List arryList = new ArrayList();
	    for(ACLCustom list : aclCustomList) {
	    	int[] a = new int[6];
	    	a[0] = list.getModuleId();
	    	a[1] = list.getCrudCreate();
	    	a[2] = list.getCrudRead();
	    	a[3] = list.getCrudUpdate();
	    	a[4] = list.getCrudDelete();
	    	a[5] = list.getExtState();
	    	arryList.add(a);
	    }	
	
		return arryList;
	}

	/**
	 * 1.查询用户端拥有的角色的所有授权，降序
	 * 2.查询用户的所有授权，并与1中的授权进行合并
	 * 3.去除那些没有读权限的摸快
	 * 4.返回有读取权限的module的集合
	 */
	@Override
	public List<Module> findAllModuleByUserId(int userId) {
		Map<Integer,ACL> aclMap = new HashMap<>();
		//一、查询用户端拥有的角色的所有授权，降序
		Map<String ,Integer> map = new HashMap<>();
		map.put("userId",userId);
		map.put("desc", 1);
		List<Role> urs = ursMapper.selectRoleListByUser(map);
		
		for(Role role : urs) {
			Map<String ,Object> paramMap = new HashMap<>();
			paramMap.put("mainType",ACL.TYPE_ROLE);
			paramMap.put("mainId",role.getId());
			List<ACL> aclList = aclMapper.findAllAclListByMainTypeMainId(paramMap);
			for(ACL acl : aclList) {
				aclMap.put(acl.getModuleId(), acl);
			}
			
			
		}
		//二、查询用户的所有授权，并与1中的授权进行合并
		Map<String ,Object> paramMap = new HashMap<>();
		paramMap.put("mainType",ACL.TYPE_USER);
		paramMap.put("mainId",userId);
		List<ACL> aclList = aclMapper.findAllAclListByMainTypeMainId(paramMap);
		for(ACL acl : aclList) {
			aclMap.put(acl.getModuleId(), acl);
		}
		
		//三、去除那些没有读权限的摸快
		Set<Entry<Integer,ACL>> entrySet = aclMap.entrySet();
		List<Integer> keyList = new ArrayList<Integer>();
		
		for(Entry<Integer, ACL> entry : entrySet) {
			int key = entry.getKey();
			ACL value = entry.getValue();
			int permission = value.getPermission(1);
			if(permission!=ACL.ACL_YES) {
				keyList.add(key);
			}
		}
		for(int moduleId : keyList) {
			aclMap.remove(moduleId);
		}
		
		//四、返回有读取权限的module的集合
		//select * from t_module where id in(1,2,3,4,6,7....)
		Set<Integer> keySet = aclMap.keySet();
		//set -> list
		List<Integer> idList = new ArrayList<>(keySet);
		List<Module> moduleList = moduleMapper.findALLModuleListByKey(idList);
		
		return moduleList;
	}
	/**
	 * 即时认证方法
	 * 
	 */
	@Override
	public boolean getPermission(int userId, int moduleId, int permission) {
		/**
		 * 1.首先查询是否给用户授权了，如果授权则使用
		 * 2.如果没有直接给用户授权，那么逐个查询其所拥有的角色的授权（按角色的优先级升序查询）
		 */
		ACL acl = this.findACL(ACL.TYPE_USER, userId, moduleId);
		if(acl!=null) {
			//有单独授权
			if(acl.getPermission(permission)!=ACL.ACL_NEUTRAL) {
				return acl.getPermission(permission)==1?true:false;
			}
		}
		//查询该用户所拥有的所有角色（按优先级）
		Map<String ,Integer> map = new HashMap<>();
		map.put("userId",userId);
		map.put("desc", 0);
		List<Role> urs = ursMapper.selectRoleListByUser(map);
		
		for(Role role : urs) {
			 acl = this.findACL(ACL.TYPE_ROLE, role.getId(), moduleId);
			 if(acl!=null) {
				 return acl.getPermission(permission)==1?true:false;
			 }
			
		}
		return false;
	}

}
