package com.xunpoit.oa.manager;

import java.security.acl.Acl;
import java.util.List;

import com.xunpoit.oa.entity.Module;

/**
 * Ȩ�������
 * @author Administrator
 *
 */
public interface ACLManager {
	
	public void addOrUpdateAcl(String mainType,int mainid, int moduleId,int permission,boolean yes);
	
	public void delAcl(String mainType,int mainid, int moduleId);
	//yes Ϊtrue�����̳У�0����false����̳У�1��
	public void updateExtendState(int userId,int moduleId,boolean yes);
	//��ʼ����Ȩҳ��
	//��������Ϊһ����ά����
	//moduleId C R U D Ext
	//1        1 2 4 8  0
	//2        1 0 0 0  0
	//.....
	public List findAllAclByMainTypeMainId(String mainType,int mainid); 
	
	
	//��½���ѯ�����û����ж�ȡȨ�޵�ģ��
	public List<Module> findAllModuleByUserId(int userId);
	
	//��ʱ��֤  ��һ���û���¼����֤���û���ĳһģ���ĳһ�����Ƿ�����
	public boolean getPermission (int userId,int moduleId,int permission);

}
