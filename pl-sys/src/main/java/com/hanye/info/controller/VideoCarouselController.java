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
import com.hanye.info.service.VideoCarouselService;
import com.hanye.info.service.VideoService;
import com.hanye.info.vo.CategoryVO;
import com.hanye.info.vo.VideoCarouselVO;
import com.hanye.info.vo.VideoVO;

@Controller
@RequestMapping("/auth/videoCarousel")
public class VideoCarouselController {
	
	@Autowired
	private VideoCarouselService videoCarouselService;

	@GetMapping("/edit")
	public String edit(Model model) {
		model.addAttribute("videoCarousel", videoCarouselService.findAll().getVideoCarouselVO());
		return "videoCarousel/edit";
	}

	@PostMapping("/editSubmit")
	public String editSubmit(@ModelAttribute VideoCarouselVO videoCarouselVO) {
		videoCarouselService.saveVideo(videoCarouselVO);
		return "redirect:/auth/videoCarousel/edit";
	}
}
