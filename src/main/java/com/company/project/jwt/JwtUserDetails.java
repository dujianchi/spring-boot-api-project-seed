package com.company.project.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 安全用户模型
 *
 * @author hackyo
 * Created on 2017/12/8 9:20.
 */
public class JwtUserDetails implements UserDetails {

    private final String mUserId;
    private final String mPassword;
    private final Collection<? extends GrantedAuthority> mAuthorities;

    @Autowired
    public JwtUserDetails(String userId, String password, Collection<? extends GrantedAuthority> authorities) {
        mUserId = userId;
        mPassword = password;
        mAuthorities = authorities;
    }

    @Override
    public String getUsername() {
        return mUserId;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return mPassword;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return mAuthorities;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

}