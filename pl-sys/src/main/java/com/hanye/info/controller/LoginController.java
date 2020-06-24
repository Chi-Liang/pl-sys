package com.hanye.info.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

@Controller
public class LoginController {
	
	@GetMapping("/")
    public String index() {
        return "login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/loginError")
    public String loginError(Model model, @RequestAttribute String errorMsg) {
        model.addAttribute("loginError", true);
        model.addAttribute("errorMsg", errorMsg);

        return "login";
    }
    
    @GetMapping("/auth/dashboard")
    public String dashboard(Model model) {
        
        return "dashboard";
    }
}
