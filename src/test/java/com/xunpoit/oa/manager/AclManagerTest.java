package com.xunpoit.oa.manager;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xunpoit.oa.entity.ACL;
import com.xunpoit.oa.entity.Module;
import com.xunpoit.oa.util.Permission;

/**
 * ≤‚ ‘¿‡
 * @author Administrator
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:spring.xml","classpath:spring-mybatis.xml"})
public class AclManagerTest {
	
	@Autowired
	private ACLManager aclManager;
	
	@Test
	public void testAddOrg(){
		aclManager.addOrUpdateAcl(ACL.TYPE_ROLE, 1, 2, Permission.CRUD_R, true);
		System.out.println("OK.......");
	}
	
	@Test
	public void testdelOrg(){
		aclManager.delAcl(ACL.TYPE_ROLE, 1,2 );
		System.out.println("OK.......");
	}
	@Test
	public void updateextstate(){
		aclManager.updateExtendState(1, 1, true);
		System.out.println("OK.......");
	}
	@Test
	public void findAllAclByMainTypeMainId(){
		List aclList = aclManager.findAllAclByMainTypeMainId(ACL.TYPE_ROLE, 1);
		for (int i=0;i<aclList.size();i++) {
			int  a[] = (int[]) aclList.get(i);
			
			System.out.println(a[0]+" "+a[1]+" "+a[2]+" "+a[3]+" "+a[4]+" "+a[5]);
		}
		System.out.println("OK.......");
	}
	@Test
	public void findAllModuleByUserId(){
		List<Module> moduleList = aclManager.findAllModuleByUserId(3);
		for (int i=0;i<moduleList.size();i++) {
			 Module module = moduleList.get(i);
			
			System.out.println(module.getName()+" "+module.getId());
		}
		System.out.println("OK.......");
	}
	@Test
	public void getPermission(){
		 boolean permission = aclManager.getPermission(3, 30, Permission.CRUD_D);
		if(permission) {
			System.out.println("‘ –Ì");
		}
		System.out.println("OK.......");
	}

}
