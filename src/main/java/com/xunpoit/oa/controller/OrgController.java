package com.xunpoit.oa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.xunpoit.oa.entity.Org;
import com.xunpoit.oa.entity.Pager;
import com.xunpoit.oa.manager.OrgManager;
import com.xunpoit.oa.web.PageModel;

@Controller
@RequestMapping("org")
public class OrgController {
	
	@Autowired
	private OrgManager orgManager;
	
	
	@RequestMapping(value="/addUI",method=RequestMethod.GET)
	public String addUI(int pid,Model model) {
		model.addAttribute("pid", pid);
		return "org/org_add";
	}
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public ModelAndView add(Org org ,int pid) {
		ModelAndView mv = new ModelAndView("common/pub_add_success");
		orgManager.addOrg(org, pid);
		return mv;
	}
	@RequestMapping(value="/del",method=RequestMethod.GET)
	public String del(int id) {
		System.out.println("进入删除");
		orgManager.delOrgById(id);
		return "common/pub_del_success";
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
	public String findAll(int pid,Pager pager ,Model model) {
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
		return "org/index";
	}
	@InitBinder("pager")
	public void initBinder(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("pager.");
	}

	
	
	

}
