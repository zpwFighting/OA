package com.xunpoit.oa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xunpoit.oa.entity.ACL;
import com.xunpoit.oa.entity.Module;
import com.xunpoit.oa.entity.Person;
import com.xunpoit.oa.entity.Role;
import com.xunpoit.oa.entity.User;
import com.xunpoit.oa.manager.ACLManager;
import com.xunpoit.oa.manager.ModuleManager;
import com.xunpoit.oa.manager.RoleManager;
import com.xunpoit.oa.manager.UserManager;
import com.xunpoit.oa.web.PageModel;

@Controller
@RequestMapping("acl")
public class ACLController {
	
	@Autowired
	private ACLManager aclManager;
	@Autowired
	private ModuleManager moduleManager;
	@Autowired
	private RoleManager roleManager;
	@Autowired
	private UserManager userManager;
	
	@RequestMapping(value="/addUI" ,method=RequestMethod.GET)
	public String addUI(String mainType,int mainId,Model model) {
		//查出所有的摸快
		PageModel<Module> pm = moduleManager.findAll(0, 0, Integer.MAX_VALUE);
		model.addAttribute("pm", pm);
		if(mainType.equals(ACL.TYPE_ROLE)) {
			//查出角色信息
			Role role = roleManager.findRoleById(mainId);
			model.addAttribute("role", role);
		}else {
			//查出用户信息
			 User user = userManager.findUserById(mainId);
			model.addAttribute("user", user);
		}
		model.addAttribute("mainType", mainType);
		model.addAttribute("mainId", mainId);
		return "acl/index";
	}

}
