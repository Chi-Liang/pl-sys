package com.hanye.info.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hanye.info.exception.PLExceptionCode;
import com.hanye.info.service.CategoryService;
import com.hanye.info.service.ContractGroupService;
import com.hanye.info.service.MemberService;
import com.hanye.info.vo.CategoryVO;
import com.hanye.info.vo.MemberVO;

@Controller
@RequestMapping("/auth/member")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ContractGroupService contractGroupService;
	
	@RequestMapping("/list")
	public String list(Model model) {
		List<CategoryVO> voList = categoryService.findAll();
		boolean pageError = false;
		if(voList.size() == 0) {
			pageError = true;
			model.addAttribute("errorMsg", PLExceptionCode.CATEGORY_NOT_FOUND.getMsg());
		}
		model.addAttribute("pageError", pageError);
		model.addAttribute("memberList", memberService.findAllByNativeSql());
		
		return "member/list";
	}
	
	@GetMapping("/add")
	public String add(Model model) {
		MemberVO member = new MemberVO();
		model.addAttribute("member", member);
		model.addAttribute("categoryList", categoryService.findAll());
		model.addAttribute("contractGroupList", contractGroupService.findAll());
		
		return "member/add";
	}
	
	@PostMapping("/addSubmit")
	public String addSubmit(@ModelAttribute MemberVO memberDTO) {	
		memberService.saveMember(memberDTO);
		
		return "redirect:/auth/member/list";
	}
	
	@GetMapping("/edit")
	public String edit(@RequestParam String mid, Model model) {
		model.addAttribute("member", memberService.findMember(mid));
		model.addAttribute("categoryList", categoryService.findAll());
		model.addAttribute("contractGroupList", contractGroupService.findAll());
		return "member/edit";
	}
	
	@PostMapping("/editSubmit")
	public String editSubmit(@ModelAttribute MemberVO memberDTO) {	
		memberService.editMember(memberDTO);
		
		return "redirect:/auth/member/list";
	}
	
	@GetMapping("/changePwd")
	public String changePwd(@RequestParam String mid, Model model) {
		model.addAttribute("member", memberService.findMember(mid));
		
		return "member/changePwd";
	}
	
	@PostMapping("/changePwdSubmit")
	public String changePwdSubmit(@ModelAttribute MemberVO memberDTO) {
		memberService.changeMemberPwd(memberDTO);
		
		return "redirect:/auth/member/list";
	}
	
	@PostMapping("/delSubmit")
	public String delSubmit(@RequestParam String mid) {
		memberService.deleteMember(mid);
		
		return "redirect:/auth/member/list";
	}
	
	@PostMapping("/downloadExcel")
	public void downloadExcel(HttpServletRequest request, 
	HttpServletResponse response) throws Exception { 
		memberService.downloadExcel(request,response);
	}
}
