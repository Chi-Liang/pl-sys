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
import com.hanye.info.service.CategoryService;
import com.hanye.info.service.VideoService;
import com.hanye.info.vo.CategoryVO;
import com.hanye.info.vo.VideoVO;

@Controller
@RequestMapping("/auth/video")
public class VideoController {
	
	@Autowired
	private VideoService videoService;
	@Autowired
	private CategoryService categoryService;

	@RequestMapping("/list")
	public String list(Model model) {
		List<CategoryVO> voList = categoryService.findAll();
		boolean pageError = false;
		if(voList.size() == 0) {
			pageError = true;
			model.addAttribute("errorMsg", PLExceptionCode.CATEGORY_NOT_FOUND.getMsg());
		}
		model.addAttribute("pageError", pageError);
		model.addAttribute("videoList", videoService.findAll());

		return "video/list";
	}

	@GetMapping("/add")
	public String add(Model model) {
		VideoVO video = new VideoVO();
		model.addAttribute("video", video);
		model.addAttribute("categoryList", categoryService.findAll());

		return "video/add";
	}

	@PostMapping("/addSubmit")
	public String addSubmit(@ModelAttribute VideoVO videoDTO) {
		videoService.saveVideo(videoDTO);

		return "forward:/auth/video/list";
	}

	@GetMapping("/edit")
	public String edit(@RequestParam Long vid, Model model) {
		model.addAttribute("video", videoService.findVideo(vid));
		model.addAttribute("categoryList", categoryService.findAll());

		return "video/edit";
	}

	@PostMapping("/editSubmit")
	public String editSubmit(@ModelAttribute VideoVO videoDTO) {
		videoService.editVideo(videoDTO);

		return "forward:/auth/video/list";
	}

	@PostMapping("/delSubmit")
	public String delSubmit(@RequestParam Long vid) {
		videoService.deleteVideo(vid);

		return "forward:/auth/video/list";
	}
}
