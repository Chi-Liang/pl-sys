package com.hanye.info.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class SysLoginFailureHandler implements AuthenticationFailureHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        handle(request, response, exception);
    }

    protected void handle(HttpServletRequest request,
                          HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String errorMsg = exception.getMessage();
        request.setAttribute("errorMsg", errorMsg);
        String targetUrl = "/loginError";

        request.getRequestDispatcher(targetUrl).forward(request, response);

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }
}
