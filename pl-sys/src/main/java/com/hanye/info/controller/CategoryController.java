package com.hanye.info.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hanye.info.service.CategoryService;
import com.hanye.info.vo.CategoryVO;

@Controller
@RequestMapping("/auth/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping("/list")
	public String list(Model model) {
		model.addAttribute("categoryList", categoryService.findAll());
		
		return "category/list";
	}
	
	@GetMapping("/add")
	public String add(Model model) {
		CategoryVO category = new CategoryVO();
		model.addAttribute("category", category);
		
		return "category/add";
	}
	
	@PostMapping("/addSubmit")
	public String addSubmit(@ModelAttribute CategoryVO categoryDTO) {	
		categoryService.saveCategory(categoryDTO);
		
		return "forward:/auth/category/list";
	}
	
	@GetMapping("/edit")
	public String edit(@RequestParam Long cid, Model model) {
		model.addAttribute("category", categoryService.findCategory(cid));
		
		return "category/edit";
	}
	
	@PostMapping("/editSubmit")
	public String editSubmit(@ModelAttribute CategoryVO categoryDTO) {	
		categoryService.editCategory(categoryDTO);
		
		return "forward:/auth/category/list";
	}
	
	@PostMapping("/delSubmit")
	public String delSubmit(@RequestParam Long cid) {
		categoryService.deleteCategory(cid);
		
		return "forward:/auth/category/list";
	}
	
}
