package com.hanye.info.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hanye.info.model.ContractGroup;
import com.hanye.info.service.CategoryService;
import com.hanye.info.service.ContractGroupService;
import com.hanye.info.vo.CategoryVO;
import com.hanye.info.vo.ContractGroupVO;
import com.hanye.info.vo.ContractVO;

@Controller
@RequestMapping("/auth/contractGroup")
public class ContractGroupController {
	
	@Autowired
	private ContractGroupService contractGroupService;
	
	@RequestMapping("/list")
	public String list(Model model,@RequestParam(required=false) String groupId) {
		model.addAttribute("contractList", StringUtils.isBlank(groupId) ? new ArrayList<ContractVO>()
				: contractGroupService.findByGroupId(Long.valueOf(groupId)));
		
		model.addAttribute("contractGroupList", contractGroupService.findAll());
		
		model.addAttribute("checkGroupId", StringUtils.isBlank(groupId) ? "" : groupId);
		
		return "contractGroup/list";
	}
	
	@GetMapping("/add")
	public String add(Model model) {
		ContractGroupVO contractGroup = new ContractGroupVO();
		model.addAttribute("contractGroup", contractGroup);
		
		return "contractGroup/add";
	}
	
	@PostMapping("/addSubmit")
	public String addSubmit(@ModelAttribute ContractGroupVO contractGroupDTO) {	
		contractGroupService.saveCategory(contractGroupDTO);
		
		return "redirect:/auth/contractGroup/list";
	}
	
	@GetMapping("/edit")
	public String edit(@RequestParam Long groupId, Model model) {
		model.addAttribute("contractGroup", contractGroupService.findContractGroup(groupId));
		
		return "contractGroup/edit";
	}
	
	@PostMapping("/editSubmit")
	public String editSubmit(@ModelAttribute ContractGroupVO contractGroupVO) {	
		
		contractGroupService.editContractGroup(contractGroupVO);
		
		return "redirect:/auth/contractGroup/list";
	}
	
	@PostMapping("/downloadPdf")
	public void downloadExcel(HttpServletRequest request, 
	HttpServletResponse response,@ModelAttribute ContractVO contractVO) throws Exception { 
		contractGroupService.downloadPdf(request,response,contractVO);
	}
	
	@PostMapping("/downloadDocx")
	public void downloadDocx(HttpServletRequest request, 
	HttpServletResponse response,@RequestParam Long groupId) throws Exception { 
		contractGroupService.downloadDocx(request,response,groupId);
	}	
	
}
