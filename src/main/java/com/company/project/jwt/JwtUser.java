package com.company.project.jwt;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtUser implements UserDetails {

    private final String mUsername, mPassword;
    private final boolean mEnabled, mAccountNonExpired, mAccountNonLocked, mCredentialsNonExpired;
    private final Collection<? extends GrantedAuthority> mAuthorities;

    public JwtUser(String username, String password
            , boolean enabled, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired
            , Collection<? extends GrantedAuthority> authorities) {
        mUsername = username;
        mPassword = password;
        mEnabled = enabled;
        mAccountNonExpired = accountNonExpired;
        mAccountNonLocked = accountNonLocked;
        mCredentialsNonExpired = credentialsNonExpired;
        mAuthorities = authorities;
    }

    @Override
    @JSONField(serialize = false, deserialize = false)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return mAuthorities;
    }

    @Override
    @JSONField(serialize = false, deserialize = false)
    public String getPassword() {
        return mPassword;
    }

    @Override
    @JSONField(serialize = false, deserialize = false)
    public String getUsername() {
        return mUsername;
    }

    @Override
    @JSONField(serialize = false, deserialize = false)
    public boolean isAccountNonExpired() {
        return mAccountNonExpired;
    }

    @Override
    @JSONField(serialize = false, deserialize = false)
    public boolean isAccountNonLocked() {
        return mAccountNonLocked;
    }

    @Override
    @JSONField(serialize = false, deserialize = false)
    public boolean isCredentialsNonExpired() {
        return mCredentialsNonExpired;
    }

    @Override
    @JSONField(serialize = false, deserialize = false)
    public boolean isEnabled() {
        return mEnabled;
    }
}
