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
import com.hanye.info.service.LectureService;
import com.hanye.info.service.VideoService;
import com.hanye.info.vo.CategoryVO;
import com.hanye.info.vo.LectureVO;
import com.hanye.info.vo.MemberVO;
import com.hanye.info.vo.ModPictureVO;

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
	
	@GetMapping("/add")
	public String add(Model model) {
		LectureVO lecture = new LectureVO();
//		lecture.setWhichGroup("1");
		model.addAttribute("lecture", lecture);
		
		return "lecture/add";
	}
	
	@PostMapping("/addSubmit")
	public String addSubmit(@ModelAttribute LectureVO lectureDTO) {	
		lectureService.saveCategory(lectureDTO);
		
		return "redirect:/auth/lecture/list";
	}
	
	@GetMapping("/edit")
	public String edit(@RequestParam Long id, Model model) {
		model.addAttribute("lecture", lectureService.findCategory(id));
		
		return "lecture/edit";
	}
	
	@PostMapping("/editSubmit")
	public String editSubmit(@ModelAttribute LectureVO lectureDTO) {	
		lectureService.editCategory(lectureDTO);
		
		return "redirect:/auth/lecture/list";
	}
	
	@PostMapping("/delSubmit")
	public String delSubmit(@RequestParam Long id) {
		lectureService.deleteCategory(id);
		
		return "redirect:/auth/lecture/list";
	}
	
	@GetMapping("/modPicture")
	public String modPicture(Model model) {
		model.addAttribute("modPicture", lectureService.findModPicture().getModPictureVO());
		return "lecture/modPicture";
	}
	
	@PostMapping("/modPictureSubmit")
	public String modPictureSubmit(@ModelAttribute ModPictureVO modPictureVO) {	
		lectureService.modPicture(modPictureVO);
		return "redirect:/auth/lecture/list";
	}
	
}
