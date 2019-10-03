package com.xunpoit.oa.manager;

import com.xunpoit.oa.entity.Person;
import com.xunpoit.oa.web.PageModel;

public interface PersonManager {

	public void addPerson(Person person,int orgId);
	
	public void delPerson(int id);
	
	public void modifyPerson(Person person);
	
	public Person finPeronById(int id);
	//∑÷“≥≤È
	public PageModel<Person> findAll(int offset,int pageSize);  
}
