package com.hanye.info.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import com.hanye.info.service.CategoryService;
import com.hanye.info.service.MemberService;
import com.hanye.info.service.PersonInfoService;
import com.hanye.info.service.VideoService;
import com.hanye.info.vo.CategoryVO;
import com.hanye.info.vo.LoginVO;
import com.hanye.info.vo.PersonInfoVO;
import com.hanye.info.vo.VideoVO;


@RestController
@RequestMapping("/api")
public class APIController {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private VideoService videoService;
	
	@Autowired
	private PersonInfoService personInfoService;
	
	@GetMapping("/category/list")
	public List<CategoryVO> findCategoryByMember(@RequestParam String mid) {
		
		return categoryService.findCategoryByMember(mid);
	}
	
	@GetMapping("/member/check")
	public LoginVO checkMember(@RequestParam String mid, @RequestParam String pwd) {
		return memberService.checkMember(mid, pwd);
	}
	
	@GetMapping("/video/list")
	public VideoVO findVideoByCategory(@RequestParam Long cid) {
		
		return videoService.findVedioByCategory(cid);
	}
	
	@PostMapping("/personInfo/insertOrUpdate")
	public String personInfo(@RequestBody PersonInfoVO personInfoVO) {
		personInfoService.editPersonInfo(personInfoVO);
		
		return "success";
	}
	
	@GetMapping("/personInfo/byMember")
	public PersonInfoVO findPersonInfoByMember(@RequestParam String mid) {
		List<PersonInfoVO> personInfoVOList = personInfoService.findAll();
		PersonInfoVO personInfo = new PersonInfoVO();
		for (PersonInfoVO personInfoVO : personInfoVOList) {
			if( StringUtils.equals(mid, personInfoVO.getMid())) {
				personInfo = personInfoVO;
				break;
			}
		}
		
		return personInfo;
	}
}
