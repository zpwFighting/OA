package com.xunpoit.oa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.xunpoit.oa.entity.Org;
import com.xunpoit.oa.manager.OrgManager;

@Controller
@RequestMapping("org")
public class OrgController {
	
	@Autowired
	private OrgManager orgManager;

	@RequestMapping(value="/add",method=RequestMethod.POST)
	public ModelAndView add(Org org ,int pid) {
		ModelAndView mv = new ModelAndView("org/index");
		orgManager.addOrg(org, pid);
		return mv;
	}
	@RequestMapping(value="/del",method=RequestMethod.GET)
	public String del(int id) {
		orgManager.delOrgById(id);
		return "del_success";
	}
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public String update(Org org) {
		orgManager.modifyOrg(org);
		
		return "update_success";
	}
	@RequestMapping(value="/findOrg",method=RequestMethod.GET)
	public String findOrg(int id,Model model) {
		Org org = orgManager.findOrgById(id);
		model.addAttribute("org", org);
		return "org_info";
	}
	@RequestMapping(value="/findAll",method=RequestMethod.GET)
	public String findAll(int pid,Model model) {
		List<Org> orgList = orgManager.findAll(pid);
		model.addAttribute("orgList", orgList);
		
		return "org/index";
	}
	

	
	
	

}
