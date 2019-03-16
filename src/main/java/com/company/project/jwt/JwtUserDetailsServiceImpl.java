package com.company.project.jwt;

import com.company.project.model.User;
import com.company.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 用户验证方法
 *
 * @author hackyo
 * Created on 2017/12/8 9:18.
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    private final UserService mUserService;

    @Autowired
    public JwtUserDetailsServiceImpl(UserService userService) {
        mUserService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        User user = mUserService.findById(id);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with id '%s'.", id));
        } else {
            return new JwtUserDetails(String.valueOf(user.getId()), user.getPassword(), JwtTokenUtil.toGrantedAuthorities(user.getRoleId()));
        }
    }

}
