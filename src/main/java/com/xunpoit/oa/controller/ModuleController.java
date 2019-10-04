package com.xunpoit.oa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.xunpoit.oa.entity.Module;
import com.xunpoit.oa.entity.Pager;
import com.xunpoit.oa.manager.ModuleManager;
import com.xunpoit.oa.web.PageModel;

@Controller
@RequestMapping("/module")
public class ModuleController {
	@Autowired
	private ModuleManager moduleManager;
	
	
	@RequestMapping(value="/addUI",method=RequestMethod.GET)
	public String addUI(int pid,Model model) {
		model.addAttribute("pid", pid);
		return "module/module_add";
	} 
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public ModelAndView add(Module module ,int pid) {
		ModelAndView mv = new ModelAndView("common/pub_add_success");
		moduleManager.addModule(module, pid);
		return mv;
	}
	@RequestMapping(value="/del",method=RequestMethod.GET)
	public String del(int id) {
		moduleManager.delModuleById(id);
		return "common/pub_del_success";
	}
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public String update(Module module) {
		moduleManager.modityModule(module);
		return "update_success";
	}
	@RequestMapping(value="/findModle",method=RequestMethod.GET)
	public String findOrg(int id,Model model) {
		Module module = moduleManager.findModuleById(id);
		model.addAttribute("module", module);
		return "module_info";
	}
	@RequestMapping(value="/findAll",method=RequestMethod.GET)
	public String findAll(int pid,Pager pager ,Model model) {
		//传的参数是pager.offset 
		PageModel<Module> pm = moduleManager.findAll(pid,pager==null? 0:pager.getOffset(),3);
		model.addAttribute("pm", pm);
		model.addAttribute("pid", pid);
		
		return "module/index";
	}
	@InitBinder("pager")
	public void initBinder(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("pager.");
	}

}
