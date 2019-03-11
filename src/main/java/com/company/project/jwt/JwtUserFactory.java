package com.company.project.jwt;

import com.company.project.model.SeedUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JwtUserFactory {

    public static final String SUPER_ADMIN = "super_admin", ADMIN = "admin", USER = "user", NONE = "none";

    private JwtUserFactory() {
    }

    public static JwtUser create(SeedUser user) {
        return new JwtUser(
                user.getName(),
                user.getPassword(),
                user.getEnable(),
                false, false, false
                , mapToGrantedAuthorities(user.getRoleId())
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(byte role) {
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
