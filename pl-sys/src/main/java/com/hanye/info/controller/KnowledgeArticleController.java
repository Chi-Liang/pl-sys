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
import com.hanye.info.service.VideoService;
import com.hanye.info.vo.KnowledgeArticleVO;
import com.hanye.info.vo.LatestInfoVO;
import com.hanye.info.vo.MemberVO;

@Controller
@RequestMapping("/auth/knowledgeArticle")
public class KnowledgeArticleController {
	
	@Autowired
	private MemberService memberService;
	@Autowired
	private KnowledgeArticleService knowledgeArticleService;
	
	
	@RequestMapping("/list")
	public String list(Model model) {
		List<MemberVO> memberList = memberService.findAll();
		boolean pageError = false;
		if(memberList.size() == 0) {
			pageError = true;
			model.addAttribute("errorMsg", PLExceptionCode.DATA_NOT_FOUND.getMsg());
		}
		model.addAttribute("pageError", pageError);
		model.addAttribute("knowledgeArticleList", knowledgeArticleService.findAll().getKnowledgeArticleVOList());

		return "knowledgeArticle/list";
	}
	
	@GetMapping("/add")
	public String add(Model model) {
		KnowledgeArticleVO knowledgeArticleVO = new KnowledgeArticleVO();
		model.addAttribute("knowledgeArticle", knowledgeArticleVO);
		
		return "knowledgeArticle/add";
	}
	
	@PostMapping("/addSubmit")
	public String addSubmit(@ModelAttribute KnowledgeArticleVO KnowledgeArticleVO) {	
		knowledgeArticleService.saveCategory(KnowledgeArticleVO);
		
		return "forward:/auth/knowledgeArticle/list";
	}
	
	@GetMapping("/edit")
	public String edit(@RequestParam Long id, Model model) {
		model.addAttribute("knowledgeArticle", knowledgeArticleService.findCategory(id));
		
		return "knowledgeArticle/edit";
	}
	
	@PostMapping("/editSubmit")
	public String editSubmit(@ModelAttribute KnowledgeArticleVO knowledgeArticleVO) {	
		knowledgeArticleService.editCategory(knowledgeArticleVO);
		
		return "forward:/auth/knowledgeArticle/list";
	}
	
	@PostMapping("/delSubmit")
	public String delSubmit(@RequestParam Long id) {
		knowledgeArticleService.deleteCategory(id);
		
		return "forward:/auth/knowledgeArticle/list";
	}

}
