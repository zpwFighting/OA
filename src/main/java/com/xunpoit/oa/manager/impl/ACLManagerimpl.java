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
		// ��ѯ�ǹ��������ACL,������ڸ��£���������ھͲ��룬
		ACL acl = this.findACL(mainType, mainId, moduleId);
		if(acl!=null) {
			//����,����
			//�ı�acl_state
			acl.setPermission(permission, yes);
			//�������ݿ�
			aclMapper.updateByPrimaryKeySelective(acl);
			return ;
		}
		//�����ڣ����
		acl = new ACL();
		acl.setMainId(mainId);
		acl.setMainType(mainType);
		acl.setModuleId(moduleId);
		//����acl_state
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

	//��ʼ����Ȩҳ��
	/**
	 * mysql>  select module_id,acl_state&1,acl_state&2,acl_state&4,acl_state&8,extend_state 
	 *         from t_acl 
	 *         where main_type='Role' and main_id=1;
	 */
	@Override
	public List findAllAclByMainTypeMainId(String mainType, int mainId) {
		//��Ȩ�޹���ҳ��ʱ  ��ͨ�����������ѯ���ݿ�   �õ�ĳĳ������Ȩ��    ������ʾ
		//���ظ�ʽ��һ����ά����
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
	 * 1.��ѯ�û���ӵ�еĽ�ɫ��������Ȩ������
	 * 2.��ѯ�û���������Ȩ������1�е���Ȩ���кϲ�
	 * 3.ȥ����Щû�ж�Ȩ�޵�����
	 * 4.�����ж�ȡȨ�޵�module�ļ���
	 */
	@Override
	public List<Module> findAllModuleByUserId(int userId) {
		Map<Integer,ACL> aclMap = new HashMap<>();
		//һ����ѯ�û���ӵ�еĽ�ɫ��������Ȩ������
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
		//������ѯ�û���������Ȩ������1�е���Ȩ���кϲ�
		Map<String ,Object> paramMap = new HashMap<>();
		paramMap.put("mainType",ACL.TYPE_USER);
		paramMap.put("mainId",userId);
		List<ACL> aclList = aclMapper.findAllAclListByMainTypeMainId(paramMap);
		for(ACL acl : aclList) {
			aclMap.put(acl.getModuleId(), acl);
		}
		
		//����ȥ����Щû�ж�Ȩ�޵�����
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
		
		//�ġ������ж�ȡȨ�޵�module�ļ���
		//select * from t_module where id in(1,2,3,4,6,7....)
		Set<Integer> keySet = aclMap.keySet();
		//set -> list
		List<Integer> idList = new ArrayList<>(keySet);
		List<Module> moduleList = moduleMapper.findALLModuleListByKey(idList);
		
		return moduleList;
	}
	/**
	 * ��ʱ��֤����
	 * 
	 */
	@Override
	public boolean getPermission(int userId, int moduleId, int permission) {
		/**
		 * 1.���Ȳ�ѯ�Ƿ���û���Ȩ�ˣ������Ȩ��ʹ��
		 * 2.���û��ֱ�Ӹ��û���Ȩ����ô�����ѯ����ӵ�еĽ�ɫ����Ȩ������ɫ�����ȼ������ѯ��
		 */
		ACL acl = this.findACL(ACL.TYPE_USER, userId, moduleId);
		if(acl!=null) {
			//�е�����Ȩ
			if(acl.getPermission(permission)!=ACL.ACL_NEUTRAL) {
				return acl.getPermission(permission)==1?true:false;
			}
		}
		//��ѯ���û���ӵ�е����н�ɫ�������ȼ���
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
