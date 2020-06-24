package com.hanye.info.security;

import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class SysLoginFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private SysLoginSuccessHandler sysLoginSuccessHandler;

    @Autowired
    private SysLoginFailureHandler sysLoginFailureHandler;

    public SysLoginFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url, HttpMethod.POST.toString()));
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {

        String uid = request.getParameter("username");
        String pwd = request.getParameter("password");
        SysUserToken sysUserToken =
                new SysUserToken(uid, pwd);

        return this.getAuthenticationManager().authenticate(sysUserToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        sysLoginSuccessHandler.onAuthenticationSuccess(request, response, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        sysLoginFailureHandler.onAuthenticationFailure(request, response, failed);
    }

}
