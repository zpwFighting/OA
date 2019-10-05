package com.xunpoit.oa.controller;

import java.util.List;

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
import com.xunpoit.oa.entity.Role;
import com.xunpoit.oa.entity.User;
import com.xunpoit.oa.entity.UsersRoles;
import com.xunpoit.oa.manager.PersonManager;
import com.xunpoit.oa.manager.RoleManager;
import com.xunpoit.oa.manager.UserManager;
import com.xunpoit.oa.web.PageModel;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserManager userManager;
	@Autowired
	private RoleManager rolemanager;
	
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
		//两种删除方法  
		//    1.根据personId在删除
		userManager.delUserByPerson(personId);
		//    2根据user表的id删除  以为person建立了双向通道可以获取到user的id
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
	//给用户分配角色（1）
	//  1.查询用户已有的角色的    2.查询user的名字  
	@RequestMapping(value="/findRoleList",method=RequestMethod.GET)
	public String findRoleLsit(int userId ,Model model) {
		//1.查询已有的角色
		List<UsersRoles> roleList = userManager.findRoleListByUser(userId);
		//2.查询user对应person的名字  因为user中有personid外键所以可以通过user得到person信息
		User user= userManager.findUserById(userId);
		model.addAttribute("user", user);
		model.addAttribute("roleList", roleList);
		//跳转到分配角色界面
		return "user/role_list";
	}
	//删除角色
	@RequestMapping(value="/delUserRole",method=RequestMethod.GET)
	public String delUserRole(int ursId) {
		userManager.delUserRoleById(ursId);
		return "common/pub_del_success";
	}
	//给用户分配角色（2）
	//1.查询所有角色   2.跳转到分配角色页面
	@RequestMapping(value="/addRoleToUser",method=RequestMethod.GET)
	public String addRoleToUser(int userId,Model model) {
		List<Role> roleList = rolemanager.findAll();
		model.addAttribute("roleList", roleList);
		model.addAttribute("userId", userId);
		return "user/select_role";
	}
	///给用户分配角色（3）最终完成添加
	@RequestMapping(value="/addUrs",method=RequestMethod.POST)
	public String addUrs(UsersRoles urs) {
		userManager.addUserRole(urs);
		return "common/pub_add_success";
	}
	@InitBinder("pager")
	public void initBinder(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("pager.");
	}
	
	
	
}
