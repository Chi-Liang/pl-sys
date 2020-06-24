package com.hanye.info.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.hanye.info.exception.PLException;

@ControllerAdvice
public class PLExceptionHandler {
	
	@ExceptionHandler(PLException.class)
    public RedirectView plException(HttpServletRequest request, PLException ex){
		String redirect = request.getRequestURI();
		RedirectView rw = new RedirectView(this.getTargetUrl(redirect));
	    FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
	    if (outputFlashMap != null){
	        outputFlashMap.put("pageError", true);
	        outputFlashMap.put("errorMsg", ex.getCode().getMsg());
	    }
	    
	    return rw;
    }
	
	private String getTargetUrl(String redirect) {
		String url = "";
		if(redirect.contains("category")) {
			url = "/auth/category/list";
		}
		if(redirect.contains("member")) {
			url = "/auth/member/list";
		}
		if(redirect.contains("user")) {
			url = "/auth/user/list";
		}
		if(redirect.contains("video")) {
			url = "/auth/video/list";
		}
		
		return url;
	}
}
