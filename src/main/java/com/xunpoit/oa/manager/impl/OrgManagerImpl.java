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
		 *   1.
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
