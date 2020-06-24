package com.hanye.info.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hanye.info.service.UserService;
import com.hanye.info.vo.UserVO;

@Controller
@RequestMapping("/auth/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/list")
	public String list(Model model) {
		model.addAttribute("userList", userService.findAllExcludeAdmin());
		
		return "user/list";
	}
	
	@GetMapping("/add")
	public String add(Model model) {
		UserVO user = new UserVO();
		model.addAttribute("user", user);
		
		return "user/add";
	}
	
	@PostMapping("/addSubmit")
	public String addSubmit(@ModelAttribute UserVO userDTO) {	
		userService.saveUser(userDTO);
		
		return "forward:/auth/user/list";
	}
	
	@GetMapping("/edit")
	public String edit(@RequestParam String uid, Model model) {
		model.addAttribute("user", userService.findUser(uid));
		
		return "user/edit";
	}
	
	@PostMapping("/editSubmit")
	public String editSubmit(@ModelAttribute UserVO userDTO) {	
		userService.editUser(userDTO);
		
		return "forward:/auth/user/list";
	}
	
	@GetMapping("/changePwd")
	public String changePwd(@RequestParam String uid, Model model) {
		model.addAttribute("user", userService.findUser(uid));
		
		return "user/changePwd";
	}
	
	@PostMapping("/changePwdSubmit")
	public String changePwdSubmit(@ModelAttribute UserVO userDTO) {
		userService.changeUserPwd(userDTO);
		
		return "forward:/auth/user/list";
	}
	
	@PostMapping("/delSubmit")
	public String delSubmit(@RequestParam String uid) {
		userService.deleteUser(uid);
		
		return "forward:/auth/user/list";
	}
}
