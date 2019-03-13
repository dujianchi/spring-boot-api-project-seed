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

    //说是权限一定要role开头？
    public static final String SUPER_ADMIN = "role_super", ADMIN = "role_admin", USER = "role_user", NONE = "role_none";

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
            return new JwtUser(String.valueOf(user.getId()), user.getPassword(), toGrantedAuthorities(user.getRoleId()));
        }
    }

    public static List<GrantedAuthority> toGrantedAuthorities(byte role) {
        if (role == 1) {
            return Arrays.asList(new SimpleGrantedAuthority(SUPER_ADMIN), new SimpleGrantedAuthority(ADMIN), new SimpleGrantedAuthority(USER), new SimpleGrantedAuthority(NONE));
        } else if (role == 2) {
            return Arrays.asList(new SimpleGrantedAuthority(ADMIN), new SimpleGrantedAuthority(USER), new SimpleGrantedAuthority(NONE));
        } else if (role == 3) {
            return Arrays.asList(new SimpleGrantedAuthority(USER), new SimpleGrantedAuthority(NONE));
        }
        return Collections.singletonList(new SimpleGrantedAuthority(NONE));
    }
}
