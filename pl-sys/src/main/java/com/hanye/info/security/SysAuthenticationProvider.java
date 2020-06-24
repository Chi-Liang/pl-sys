package com.hanye.info.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Component;

import com.hanye.info.model.Role;
import com.hanye.info.model.User;
import com.hanye.info.repository.UserRepository;

 
@Component
public class SysAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private UserRepository  userRepository;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        SysUserToken sysUserToken = (SysUserToken)usernamePasswordAuthenticationToken;
        String pwd = userDetails.getPassword();

        if(!new BCryptPasswordEncoder().matches(
                sysUserToken.getCredentials().toString(), pwd)) {
            throw new BadCredentialsException("登入失敗，帳號或密碼不正確");
        }
    }
 
    @Override
    protected UserDetails retrieveUser(String s, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        SysUserToken sysUserToken = (SysUserToken)usernamePasswordAuthenticationToken;
        
        Optional<User> user = userRepository.findById(sysUserToken.getName());
        if(user.isEmpty()) {
            throw new BadCredentialsException("登入失敗，帳號或密碼不正確");
        }
        Set<Role> roles = user.get().getRoles();
        SysUserDetails sysUserDetails =
                new SysUserDetails(user.get().getUid(), user.get().getPwd(), this.genAuthority(roles));

        return sysUserDetails;
    }
    
    private List<GrantedAuthority> genAuthority(Set<Role> roles) {
    	List<GrantedAuthority> authorityList = new ArrayList<GrantedAuthority>();
    	for(Role role:roles) {
    		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getRid());
    		authorityList.add(authority);
    	}
    	
    	return authorityList;
    }
    
    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("1qaz2wsx"));
    }
}
