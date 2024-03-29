package com.hanye.info.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hanye.info.exception.PLExceptionCode;
import com.hanye.info.service.LectureQueryService;
import com.hanye.info.service.MemberService;
import com.hanye.info.service.PersonInfoService;
import com.hanye.info.service.VideoService;
import com.hanye.info.vo.MemberVO;

@Controller
@RequestMapping("/auth/lectureQuery")
public class LectureQueryController {
	
	@Autowired
	private MemberService memberService;
	@Autowired
	private LectureQueryService lectureQueryService;
	
	
	@RequestMapping("/list")
	public String list(Model model) {
		boolean pageError = false;
		if(!memberService.checkExistMember()) {
			pageError = true;
			model.addAttribute("errorMsg", PLExceptionCode.DATA_NOT_FOUND.getMsg());
		}
		model.addAttribute("pageError", pageError);
		model.addAttribute("lectureQueryList", lectureQueryService.findAll());

		return "lectureQuery/list";
	}
	
	@GetMapping("/query")
	public String edit(@RequestParam Long id, Model model) {
		model.addAttribute("lectureQueryList", lectureQueryService.findId(id));
		
		return "lectureQuery/query";
	}

}
