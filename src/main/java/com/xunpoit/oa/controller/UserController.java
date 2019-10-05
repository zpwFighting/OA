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
		//����ɾ������  
		//    1.����personId��ɾ��
		userManager.delUserByPerson(personId);
		//    2����user���idɾ��  ��Ϊperson������˫��ͨ�����Ի�ȡ��user��id
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
		//���Ĳ�����pager.offset 
		PageModel<Person> pm = userManager.findAll(pager==null? 0:pager.getOffset(),10);
		model.addAttribute("pm", pm);
		return "user/index";
	}
	//���û������ɫ��1��
	//  1.��ѯ�û����еĽ�ɫ��    2.��ѯuser������  
	@RequestMapping(value="/findRoleList",method=RequestMethod.GET)
	public String findRoleLsit(int userId ,Model model) {
		//1.��ѯ���еĽ�ɫ
		List<UsersRoles> roleList = userManager.findRoleListByUser(userId);
		//2.��ѯuser��Ӧperson������  ��Ϊuser����personid������Կ���ͨ��user�õ�person��Ϣ
		User user= userManager.findUserById(userId);
		model.addAttribute("user", user);
		model.addAttribute("roleList", roleList);
		//��ת�������ɫ����
		return "user/role_list";
	}
	//ɾ����ɫ
	@RequestMapping(value="/delUserRole",method=RequestMethod.GET)
	public String delUserRole(int ursId) {
		userManager.delUserRoleById(ursId);
		return "common/pub_del_success";
	}
	//���û������ɫ��2��
	//1.��ѯ���н�ɫ   2.��ת�������ɫҳ��
	@RequestMapping(value="/addRoleToUser",method=RequestMethod.GET)
	public String addRoleToUser(int userId,Model model) {
		List<Role> roleList = rolemanager.findAll();
		model.addAttribute("roleList", roleList);
		model.addAttribute("userId", userId);
		return "user/select_role";
	}
	///���û������ɫ��3������������
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
