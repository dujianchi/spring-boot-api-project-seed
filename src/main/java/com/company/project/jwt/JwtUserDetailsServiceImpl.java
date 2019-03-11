package com.company.project.jwt;

import com.company.project.core.ServiceException;
import com.company.project.model.SeedUser;
import com.company.project.service.SeedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SeedUserService mUserService;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        SeedUser user = mUserService.findById(id);
        if (user == null) {
            throw new ServiceException(String.format("No user found with id '%s'.", id));
        } else {
            return JwtUserFactory.create(user);
        }
    }
}
