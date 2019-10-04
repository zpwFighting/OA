package com.xunpoit.oa.controller;

import java.util.List;

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
import com.xunpoit.oa.entity.Role;
import com.xunpoit.oa.manager.OrgManager;
import com.xunpoit.oa.manager.PersonManager;
import com.xunpoit.oa.manager.RoleManager;
import com.xunpoit.oa.web.PageModel;

@Controller
@RequestMapping("role")
public class RoleController {

	@Autowired
	private RoleManager roleManager;
	
	
	@RequestMapping(value="/addUI",method=RequestMethod.GET)
	public String addUI() {
		return "role/role_add";
	}
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(Role role) {
		roleManager.addRole(role);
		return "common/pub_add_success";
	}
	@RequestMapping(value="/del",method=RequestMethod.GET)
	public String del(int id) {
		roleManager.delRoleById(id);
		return "common/pub_del_success";
	}
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public String update(Role role) {
		roleManager.modifyRole(role);
		return "common/pub_update_success";
	}
	@RequestMapping(value="/findRole",method=RequestMethod.GET)
	public String findRole(int id,Model model) {
		 Role role= roleManager.findRoleById(id);
		model.addAttribute("Role", role);
		return "role_info";
	}
	@RequestMapping(value="/findAll",method=RequestMethod.GET)
	public String findAll(Pager pager ,Model model) {
		//传的参数是pager.offset 
		 List<Role> list = roleManager.findAll();
		model.addAttribute("list", list);
		return "role/index";
	}
	@InitBinder("pager")
	public void initBinder(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("pager.");
	}
	
}
