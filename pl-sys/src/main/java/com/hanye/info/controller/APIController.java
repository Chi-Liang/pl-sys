package com.hanye.info.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import com.hanye.info.convert.BeanConverter;
import com.hanye.info.model.LatestInfo;
import com.hanye.info.service.CategoryService;
import com.hanye.info.service.ContactUsService;
import com.hanye.info.service.KnowledgeArticleService;
import com.hanye.info.service.LatestInfoService;
import com.hanye.info.service.LectureQueryService;
import com.hanye.info.service.LectureService;
import com.hanye.info.service.MemberService;
import com.hanye.info.service.PersonInfoService;
import com.hanye.info.service.UploadPictureService;
import com.hanye.info.service.VideoService;
import com.hanye.info.vo.ReturnVO;
import com.hanye.info.vo.ReturnVideoVO;
import com.hanye.info.vo.VideoListVO;
import com.hanye.info.vo.CategoryVO;
import com.hanye.info.vo.CheckMemberVO;
import com.hanye.info.vo.ContactUsVO;
import com.hanye.info.vo.LatestInfoVO;
import com.hanye.info.vo.LectureQueryVO;
import com.hanye.info.vo.LectureVO;
import com.hanye.info.vo.LoginVO;
import com.hanye.info.vo.MemberVO;
import com.hanye.info.vo.PersonInfoVO;
import com.hanye.info.vo.ReturnCategoryVO;
import com.hanye.info.vo.ReturnKnowledgeArticleVO;
import com.hanye.info.vo.ReturnLatestInfoVO;
import com.hanye.info.vo.ReturnLectureVO;
import com.hanye.info.vo.ReturnLoginVO;
import com.hanye.info.vo.ReturnPersonInfoVO;
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
	
	@Autowired
	private LatestInfoService latestInfoService;
	
	@Autowired
	private LectureService lectureService;
	
	@Autowired
	private ContactUsService contactUsService;
	
	@Autowired
	private LectureQueryService lectureQueryService;
	
	@Autowired
	private KnowledgeArticleService knowledgeArticleService;
	
	@Autowired
	private UploadPictureService uploadPictureService;
	
	@PostMapping("/category/list")
	public ReturnCategoryVO findCategoryByMember(@RequestBody CheckMemberVO checkMemberVO) {
		
		return categoryService.findCategoryByMember(checkMemberVO.getMid());
	}
	
	@PostMapping("/member/check")
	public ReturnLoginVO checkMember(@RequestBody CheckMemberVO checkMemberVO) {
		return memberService.checkMember(checkMemberVO);
	}
	
	@PostMapping("/video/list")
	public ReturnVideoVO findVideoByCategory(@RequestBody VideoListVO videoListVO) {
		
		return videoService.findVedioByCategory(videoListVO.getCid());
	}
	
	@PostMapping("/personInfo/insertOrUpdate")
	public LoginVO personInfo(@RequestBody PersonInfoVO personInfoVO) {
		return personInfoService.editPersonInfo(personInfoVO);
		
	}
	
	@PostMapping("/personInfo/byMember")
	public ReturnPersonInfoVO findPersonInfoByMember(@RequestBody CheckMemberVO checkMemberVO) {
		return personInfoService.findMid(checkMemberVO.getMid());
		
	}
	
	@PostMapping("/member/addMember")
	public ReturnVO addMember(@RequestBody MemberVO memberVO) {
		return memberService.addMember(memberVO);
	}
	
	@PostMapping("/lecture/list")
	public ReturnLectureVO findLectureList() {
		return lectureService.findFilterStartTime();
	}
	
	@PostMapping("/latestInfo/list")
	public ReturnLatestInfoVO findLatestInfoList() {
		return latestInfoService.findAll();
	}
	
	@PostMapping("/knowledgeArticle/list")
	public ReturnKnowledgeArticleVO findKnowledgeArticleList() {
		return knowledgeArticleService.findAll();
	}
	
	@PostMapping("/lecture/save")
	public LoginVO lectureSave(@RequestBody LectureQueryVO lectureQueryVO) {
		return lectureQueryService.save(lectureQueryVO);
		
	}
	
	@PostMapping("/contactUs/addContactUs")
	public ReturnVO addContactUs(@RequestBody ContactUsVO contactUsVO) {
		return contactUsService.addContactUs(contactUsVO);
	}
	
	@RequestMapping(value = "/getPhoto/{imgUrl}", produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] getPhoto(@PathVariable("imgUrl") String imgUrl) {
		return uploadPictureService.getPhoto(imgUrl);
	}
	
	@RequestMapping(value = "/getPhotoLatestInfo/{lid}", produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] getPhotoLatestInfo(@PathVariable("lid") Long lid) {
		return latestInfoService.findCategory(lid).getPicture();
	}
	
	@RequestMapping(value = "/getBigPhotoKnowledgeArticle/{lid}", produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] getPhotoKnowledgeArticle(@PathVariable("lid") Long lid) {
		return knowledgeArticleService.findCategory(lid).getBigPicture();
	}
	
	@RequestMapping(value = "/getSmallPhotoKnowledgeArticle/{lid}", produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] getSmallPhotoKnowledgeArticle(@PathVariable("lid") Long lid) {
		return knowledgeArticleService.findCategory(lid).getSmallPicture();
	}
	
	@RequestMapping(value = "/getPhotoVideo/{vid}", produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] getPhotoVideo(@PathVariable("vid") Long vid) {
		return videoService.findCategory(vid).getPicture();
	}
	
	@PostMapping("/changePassword")
	public ReturnVO changePassword(@RequestBody MemberVO memberDTO) {
		return memberService.changeMemberPwd(memberDTO);
		
	}
	
}
