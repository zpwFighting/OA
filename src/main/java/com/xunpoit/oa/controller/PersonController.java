package com.xunpoit.oa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xunpoit.oa.entity.Org;
import com.xunpoit.oa.entity.Pager;
import com.xunpoit.oa.entity.Person;
import com.xunpoit.oa.manager.OrgManager;
import com.xunpoit.oa.manager.PersonManager;
import com.xunpoit.oa.web.PageModel;

@Controller
@RequestMapping("person")
public class PersonController {

	@Autowired
	private PersonManager personManager;
	@Autowired
	private OrgManager orgManager;
	
	@RequestMapping(value="/addUI",method=RequestMethod.GET)
	public String addUI() {
		return "person/person_add";
	}
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(Person person,int orgId) {
		personManager.addPerson(person, orgId);
		return "common/pub_add_success";
	}
	@RequestMapping(value="/del",method=RequestMethod.GET)
	public String del(int id) {
		personManager.delPerson(id);
		return "common/pub_del_success";
	}
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public String update(Person person) {
		personManager.modifyPerson(person);
		return "common/pub_update_success";
	}
	@RequestMapping(value="/findPerson",method=RequestMethod.GET)
	public String findPerson(int id,Model model) {
		 Person person = personManager.finPeronById(id);
		model.addAttribute("person", person);
		return "person_info";
	}
	@RequestMapping(value="/findAll",method=RequestMethod.GET)
	public String findAll(Pager pager ,Model model) {
		//传的参数是pager.offset 
		PageModel<Person> pm = personManager.findAll(pager==null? 0:pager.getOffset(),3);
		model.addAttribute("pm", pm);
		return "person/index";
	}
	@InitBinder("pager")
	public void initBinder(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("pager.");
	}
	@RequestMapping("findAllOrg")
	public String findAllOrg(int pid,Pager pager ,Model model) {
		//传的参数是pager.offset 
		PageModel<Org> pm = orgManager.findAll(pid,pager==null? 0:pager.getOffset(),3);
		model.addAttribute("pm", pm);
		model.addAttribute("ppid", 0);
		model.addAttribute("pid", pid);
		if(pid!=0) {
			Org org = orgManager.findOrgById(pid);
			if(org.getParent()!=null) {
				model.addAttribute("ppid", org.getParent().getId());
			}
		}
		return "person/select_org";
	}
}
