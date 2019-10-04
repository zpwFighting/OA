package com.xunpoit.oa.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xunpoit.oa.dao.OrgMapper;
import com.xunpoit.oa.dao.PersonMapper;
import com.xunpoit.oa.dao.UsersRolesMapper;
import com.xunpoit.oa.entity.Org;
import com.xunpoit.oa.entity.Person;
import com.xunpoit.oa.manager.PersonManager;
import com.xunpoit.oa.web.PageModel;

@Service
public class PersonManagerImpl implements PersonManager {

	@Autowired
	private PersonMapper personMapper;
	
	@Autowired
	private OrgMapper orgMapper;
	
	
	@Override
	public void addPerson(Person person, int orgId) {
	  //先查出机构  因为通过表单添加时不能直接给Org类型中添值
	  Org org = orgMapper.selectByPrimaryKey(orgId);
	  person.setOrg(org);
	  personMapper.insertSelective(person);

	}

	@Override
	public void delPerson(int id) {
		personMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void modifyPerson(Person person){
		personMapper.updateByPrimaryKeySelective(person);
	}

	@Override
	public PageModel<Person> findAll(int offset, int pageSize) {
		PageModel<Person> pageModel = new PageModel<Person>();
		Map<String ,Integer> personMap = new HashMap<String ,Integer>();
		personMap.put("offset", offset);
		personMap.put("pageSize", pageSize);
		List<Person> dataList = personMapper.findAll(personMap);
		//查询总条数
		long items = personMapper.findItems();
		pageModel.setDataList(dataList);
		pageModel.setItems((int)items);
		pageModel.setPageSize(pageSize);
		
		return pageModel;
	}

	@Override
	public Person finPeronById(int id) {
		return personMapper.selectByPrimaryKey(id);
	}

}
