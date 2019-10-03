package com.xunpoit.oa.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xunpoit.oa.dao.OrgMapper;
import com.xunpoit.oa.entity.Org;
import com.xunpoit.oa.manager.OrgManager;
import com.xunpoit.oa.web.PageModel;

/**
 * 
 * @author Administrator
 *   
 *   知识点：
 *   	一、获取自增的主键id，在添加方法中org原本是没有设置id值的。但是在用时可以直接拿到原因如下：
 *   		1、在mybatis的insert语句中设置了      useGeneratedKeys="true" keyProperty="id"
 *   		2、其中useGeneratedKeys参数只针对 insert 语句生效，默认为 false。当设置为 true 时，
 *   		 	表示如果插入的表以自增列为主键，则允许 JDBC 支持自动生成主键，并可将自动生成的主键返回。
 *   			keyProperty="id" 使主键保存到了id中
 *   	二、事物的知识点，详细在下面
 *   	三、pageModel   分页查询知识点
 *   		1.pom文件中引入包	<groupId>jsptags</groupId>
			                <artifactId>pager-taglib</artifactId>
			                <version>2.0</version>
			2.分页模板文件 即pager.jsp文件
				需要向它传两个值：总条数items  每页多少条pageSize 将这两个封装到PageModel中 当然还有dataList；
			3.分页查询需要两个数据： 1当前页 模板会通过pager.offset自动提供   2每页条数自己设定 
				详细看OrgController-->注意如何获取pager.offset(1设置一个类叫做Pager。2.进行绑定binder.setFieldDefaultPrefix("pager.");3.获取)
 */
@Service("orgManager")
public class OrgManagerImpl implements OrgManager {

	@Autowired
	private OrgMapper orgMapper;

	@Override
	public void addOrg(Org org, int pid) {
		//添加   但是没有设置机构编号  
		//    机构编号： 1.父机构+"_"+id;  2.当没有父机构时直接为id
		Org parent = null;
		if (pid > 0) {
		    parent = orgMapper.selectByPrimaryKey(pid);
			org.setParent(parent);
		}
		orgMapper.insertSelective(org);
		//设置机构编号  初始为自己id
		String sn = ""+org.getId();
		//当有父机构时  
		if(pid>0) {
			sn = parent.getId()+"_"+org.getId();
		}
		org.setSn(sn);
		//进行更新
		orgMapper.updateByPrimaryKeySelective(org);
		//事物 spring 声明式事务管理  
		/**
		 * 声明事物要四步：
		 *   1.配置数据源 dataSource
		 *   2.配置事物管理器 transactionManager
		 *   3.配置通知transactionAdvice 设置事物传播行为
		 *   4.启用事物通知aop:config
		 */

	}

	@Override
	public void delOrgById(int id) {
		//删除时先判断是否有子机构
		Org org = orgMapper.selectByPrimaryKey(id);
		if(org.getChildList().size()>0) {
			throw new RuntimeException("有子机构，不能删除。删除失败！！！");
		}
		orgMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void modifyOrg(Org org) {
		orgMapper.updateByPrimaryKeySelective(org);
	}

	@Override
	public Org findOrgById(int id) {
		return orgMapper.selectByPrimaryKey(id);
	}

	@Override
	public PageModel<Org> findAll(int pid,int offset,int pageSize) {
		//如何给mybatis传多个值  --》 Map<key,value>
		Map<String,Integer> paramMap = new HashMap<String,Integer>();
		paramMap.put("pid", pid);
		paramMap.put("offset", offset);
		paramMap.put("pageSize", pageSize);
		List<Org> orgList = orgMapper.findAllByParent(paramMap);
		PageModel<Org> pageModel = new PageModel<Org>();
		pageModel.setDataList(orgList);
		pageModel.setPageSize(pageSize);
		//设置总条数
		long items = orgMapper.selectCount(pid);
		pageModel.setItems((int)items);
		return pageModel;
	}

}
