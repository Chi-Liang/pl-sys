package com.hanye.info.security;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class SysUserToken extends UsernamePasswordAuthenticationToken {

    public SysUserToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public SysUserToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, String memberName) {
        super(principal, credentials, authorities);
    }

}
