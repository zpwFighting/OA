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
 *   ֪ʶ�㣺
 *   	һ����ȡ����������id������ӷ�����orgԭ����û������idֵ�ġ���������ʱ����ֱ���õ�ԭ�����£�
 *   		1����mybatis��insert�����������      useGeneratedKeys="true" keyProperty="id"
 *   		2������useGeneratedKeys����ֻ��� insert �����Ч��Ĭ��Ϊ false��������Ϊ true ʱ��
 *   		 	��ʾ�������ı���������Ϊ������������ JDBC ֧���Զ��������������ɽ��Զ����ɵ��������ء�
 *   			keyProperty="id" ʹ�������浽��id��
 *   	���������֪ʶ�㣬��ϸ������
 *   	����pageModel   ��ҳ��ѯ֪ʶ��
 *   		1.pom�ļ��������	<groupId>jsptags</groupId>
			                <artifactId>pager-taglib</artifactId>
			                <version>2.0</version>
			2.��ҳģ���ļ� ��pager.jsp�ļ�
				��Ҫ����������ֵ��������items  ÿҳ������pageSize ����������װ��PageModel�� ��Ȼ����dataList��
			3.��ҳ��ѯ��Ҫ�������ݣ� 1��ǰҳ ģ���ͨ��pager.offset�Զ��ṩ   2ÿҳ�����Լ��趨 
				��ϸ��OrgController-->ע����λ�ȡpager.offset(1����һ�������Pager��2.���а�binder.setFieldDefaultPrefix("pager.");3.��ȡ)
 */
@Service("orgManager")
public class OrgManagerImpl implements OrgManager {

	@Autowired
	private OrgMapper orgMapper;

	@Override
	public void addOrg(Org org, int pid) {
		//���   ����û�����û������  
		//    ������ţ� 1.������+"_"+id;  2.��û�и�����ʱֱ��Ϊid
		Org parent = null;
		if (pid > 0) {
		    parent = orgMapper.selectByPrimaryKey(pid);
			org.setParent(parent);
		}
		orgMapper.insertSelective(org);
		//���û������  ��ʼΪ�Լ�id
		String sn = ""+org.getId();
		//���и�����ʱ  
		if(pid>0) {
			sn = parent.getId()+"_"+org.getId();
		}
		org.setSn(sn);
		//���и���
		orgMapper.updateByPrimaryKeySelective(org);
		//���� spring ����ʽ�������  
		/**
		 * ��������Ҫ�Ĳ���
		 *   1.��������Դ dataSource
		 *   2.������������� transactionManager
		 *   3.����֪ͨtransactionAdvice �������ﴫ����Ϊ
		 *   4.��������֪ͨaop:config
		 */

	}

	@Override
	public void delOrgById(int id) {
		//ɾ��ʱ���ж��Ƿ����ӻ���
		Org org = orgMapper.selectByPrimaryKey(id);
		if(org.getChildList().size()>0) {
			throw new RuntimeException("���ӻ���������ɾ����ɾ��ʧ�ܣ�����");
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
		//��θ�mybatis�����ֵ  --�� Map<key,value>
		Map<String,Integer> paramMap = new HashMap<String,Integer>();
		paramMap.put("pid", pid);
		paramMap.put("offset", offset);
		paramMap.put("pageSize", pageSize);
		List<Org> orgList = orgMapper.findAllByParent(paramMap);
		PageModel<Org> pageModel = new PageModel<Org>();
		pageModel.setDataList(orgList);
		pageModel.setPageSize(pageSize);
		//����������
		long items = orgMapper.selectCount(pid);
		pageModel.setItems((int)items);
		return pageModel;
	}

}
