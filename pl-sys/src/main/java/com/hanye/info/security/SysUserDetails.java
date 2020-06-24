package com.hanye.info.security;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


public class SysUserDetails extends User {

    public SysUserDetails(String username, String password, List<GrantedAuthority> authorityList) {
        super(username, password, authorityList);
    }

}
