package com.hanye.info.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hanye.info.exception.PLExceptionCode;
import com.hanye.info.service.MemberService;
import com.hanye.info.service.KnowledgeArticleService;
import com.hanye.info.service.LatestInfoService;
import com.hanye.info.service.LectureService;
import com.hanye.info.service.VideoService;
import com.hanye.info.vo.LatestInfoVO;
import com.hanye.info.vo.LectureVO;
import com.hanye.info.vo.MemberVO;

@Controller
@RequestMapping("/auth/latestInfo")
public class LatestInfoController {
	
	@Autowired
	private MemberService memberService;
	@Autowired
	private LatestInfoService latestInfoService;
	
	
	@RequestMapping("/list")
	public String list(Model model) {
		List<MemberVO> memberList = memberService.findAll();
		boolean pageError = false;
		if(memberList.size() == 0) {
			pageError = true;
			model.addAttribute("errorMsg", PLExceptionCode.DATA_NOT_FOUND.getMsg());
		}
		model.addAttribute("pageError", pageError);
		model.addAttribute("latestInfoList", latestInfoService.findAll());

		return "latestInfo/list";
	}
	
	@GetMapping("/add")
	public String add(Model model) {
		LatestInfoVO latestInfo = new LatestInfoVO();
		model.addAttribute("latestInfo", latestInfo);
		
		return "latestInfo/add";
	}
	
	@PostMapping("/addSubmit")
	public String addSubmit(@ModelAttribute LatestInfoVO latestInfoDTO) {	
		latestInfoService.saveCategory(latestInfoDTO);
		
		return "forward:/auth/latestInfo/list";
	}
	
	@GetMapping("/edit")
	public String edit(@RequestParam Long id, Model model) {
		model.addAttribute("latestInfo", latestInfoService.findCategory(id));
		
		return "latestInfo/edit";
	}
	
	@PostMapping("/editSubmit")
	public String editSubmit(@ModelAttribute LatestInfoVO latestInfoDTO) {	
		latestInfoService.editCategory(latestInfoDTO);
		
		return "forward:/auth/latestInfo/list";
	}
	
	@PostMapping("/delSubmit")
	public String delSubmit(@RequestParam Long id) {
		latestInfoService.deleteCategory(id);
		
		return "forward:/auth/latestInfo/list";
	}

}
