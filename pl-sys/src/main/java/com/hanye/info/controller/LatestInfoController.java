package com.hanye.info.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
		model.addAttribute("latestInfoList", latestInfoService.findAll().getLatestInfoVOList());

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
		
		return "redirect:/auth/latestInfo/list";
	}
	
	@GetMapping("/edit")
	public String edit(@RequestParam Long id, Model model) {
		model.addAttribute("latestInfo", latestInfoService.findCategory(id));
		
		return "latestInfo/edit";
	}
	
	@PostMapping("/editSubmit")
	public String editSubmit(@ModelAttribute LatestInfoVO latestInfoDTO) {	
		latestInfoService.editCategory(latestInfoDTO);
		
		return "redirect:/auth/latestInfo/list";
	}
	
//	@PostMapping("/uploadPicture")
//	public String uploadPicture(@RequestParam(value = "file") MultipartFile file) {	
//		if (file.isEmpty()) {
//			System.out.println("檔案為空空");
//		}
//		String fileName = file.getOriginalFilename(); // 檔名
//		String suffixName = fileName.substring(fileName.lastIndexOf(".")); // 字尾名
//		String filePath = "C:/圖片/"; // 上傳後的路徑
//		fileName = UUID.randomUUID() + suffixName; // 新檔名
//		System.out.println("fileName1111111111:" + fileName);
//		File dest = new File(filePath + fileName);
//		if (!dest.getParentFile().exists()) {
//			dest.getParentFile().mkdirs();
//		}
//		try {
//			file.transferTo(dest);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		return "forward:/auth/latestInfo/list";
//	}
	
	@PostMapping("/delSubmit")
	public String delSubmit(@RequestParam Long id) {
		latestInfoService.deleteCategory(id);
		
		return "redirect:/auth/latestInfo/list";
	}

}
