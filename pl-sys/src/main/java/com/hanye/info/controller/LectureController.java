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
import com.hanye.info.service.KnowledgeArticleService;
import com.hanye.info.service.LectureService;
import com.hanye.info.service.VideoService;
import com.hanye.info.vo.MemberVO;

@Controller
@RequestMapping("/auth/lecture")
public class LectureController {
	
	@Autowired
	private MemberService memberService;
	@Autowired
	private LectureService lectureService;
	
	
	@RequestMapping("/list")
	public String list(Model model) {
		List<MemberVO> memberList = memberService.findAll();
		boolean pageError = false;
		if(memberList.size() == 0) {
			pageError = true;
			model.addAttribute("errorMsg", PLExceptionCode.DATA_NOT_FOUND.getMsg());
		}
		model.addAttribute("pageError", pageError);
		model.addAttribute("lectureList", lectureService.findAll());

		return "lecture/list";
	}
	
	@GetMapping("/query")
	public String edit(@RequestParam Long cid, Model model) {
		model.addAttribute("lectureList", lectureService.findMember(cid));
		
		return "lecture/query";
	}

}
