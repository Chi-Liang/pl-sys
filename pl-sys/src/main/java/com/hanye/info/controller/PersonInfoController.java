package com.hanye.info.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hanye.info.exception.PLExceptionCode;
import com.hanye.info.service.MemberService;
import com.hanye.info.service.PersonInfoService;
import com.hanye.info.vo.MemberVO;

@Controller
@RequestMapping("/auth/personInfo")
public class PersonInfoController {
	
	@Autowired
	private MemberService memberService;
	@Autowired
	private PersonInfoService personInfoService;
	
	
	@RequestMapping("/list")
	public String list(Model model) {
		boolean pageError = false;
		if(!memberService.checkExistMember()) {
			pageError = true;
			model.addAttribute("errorMsg", PLExceptionCode.DATA_NOT_FOUND.getMsg());
		}
		model.addAttribute("pageError", pageError);
		model.addAttribute("personInfoList", personInfoService.findAll());

		return "personInfo/list";
	}
	
	@GetMapping("/query")
	public String edit(@RequestParam String mid, Model model) {
		model.addAttribute("personInfoList", personInfoService.findMember(mid));
		
		return "personInfo/query";
	}
	
	@PostMapping("/downloadExcel")
	public void downloadExcel(HttpServletRequest request, 
	HttpServletResponse response) throws Exception { 
		personInfoService.downloadExcel(request,response);
	}	
}
