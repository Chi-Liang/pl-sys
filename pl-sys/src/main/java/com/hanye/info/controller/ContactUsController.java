package com.hanye.info.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hanye.info.exception.PLExceptionCode;
import com.hanye.info.service.MemberService;
import com.hanye.info.service.ContactUsService;
import com.hanye.info.service.KnowledgeArticleService;
import com.hanye.info.service.LatestInfoService;
import com.hanye.info.service.LectureService;
import com.hanye.info.service.VideoService;
import com.hanye.info.vo.MemberVO;

@Controller
@RequestMapping("/auth/contactUs")
public class ContactUsController {
	
	@Autowired
	private MemberService memberService;
	@Autowired
	private ContactUsService contactUsService;
	
	
	@RequestMapping("/list")
	public String list(Model model) {
		boolean pageError = false;
		if(!memberService.checkExistMember()) {
			pageError = true;
			model.addAttribute("errorMsg", PLExceptionCode.DATA_NOT_FOUND.getMsg());
		}
		model.addAttribute("pageError", pageError);
		model.addAttribute("contactUsList", contactUsService.findAll());

		return "contactUs/list";
	}
	
	@GetMapping("/query")
	public String edit(@RequestParam Long lid, Model model) {
		model.addAttribute("contactUsList", contactUsService.findMember(lid));
		
		return "contactUs/query";
	}

}
