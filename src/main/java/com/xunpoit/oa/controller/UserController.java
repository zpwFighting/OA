package com.xunpoit.oa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.xunpoit.oa.entity.Pager;
import com.xunpoit.oa.entity.Person;
import com.xunpoit.oa.entity.User;
import com.xunpoit.oa.manager.UserManager;
import com.xunpoit.oa.web.PageModel;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserManager userManager;
	
	
	@RequestMapping(value="/addUI",method=RequestMethod.GET)
	public String addUI(Model model,int personId) {
		model.addAttribute("personId", personId);
		return "user/user_add";
	}
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public ModelAndView add(User user ,int personId) {
		ModelAndView mv = new ModelAndView("common/pub_add_success");
		userManager.addUser(user, personId);
		return mv;
	}
	@RequestMapping(value="/del",method=RequestMethod.GET)
	public String del(int personId) {
		
		userManager.delUserByPerson(personId);
		return "common/pub_del_success";
	}
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public String update(User user) {
		userManager.modityUser(user);
		
		return "update_success";
	}
	@RequestMapping(value="/findUser",method=RequestMethod.GET)
	public String findUser(int id,Model model) {
		User user = userManager.findUserById(id);
		model.addAttribute("user", user);
		return "org_info";
	}
	@RequestMapping(value="/findAll",method=RequestMethod.GET)
	public String findAll(Pager pager ,Model model) {
		//传的参数是pager.offset 
		PageModel<Person> pm = userManager.findAll(pager==null? 0:pager.getOffset(),10);
		model.addAttribute("pm", pm);
		return "user/index";
	}
	@InitBinder("pager")
	public void initBinder(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("pager.");
	}
	
}
