package com.company.project.jwt;


import com.company.project.core.ResultCode;
import com.company.project.core.ResultGenerator;
import com.company.project.util.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 安全模块配置
 *
 * @author hackyo
 * Created on 2017/12/8 9:15.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService mUserDetailsService;
    private final JwtAuthenticationTokenFilter mJwtAuthenticationTokenFilter;
    private final EntryPointUnauthorizedHandler mEntryPointUnauthorizedHandler;
    private final RestAccessDeniedHandler mRestAccessDeniedHandler;
    private final PasswordEncoder mPasswordEncoder;

    @Autowired
    public WebSecurityConfig(JwtUserDetailsServiceImpl userDetailsService, JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter, EntryPointUnauthorizedHandler entryPointUnauthorizedHandler, RestAccessDeniedHandler restAccessDeniedHandler) {
        mUserDetailsService = userDetailsService;
        mJwtAuthenticationTokenFilter = jwtAuthenticationTokenFilter;
        mEntryPointUnauthorizedHandler = entryPointUnauthorizedHandler;
        mRestAccessDeniedHandler = restAccessDeniedHandler;
        mPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(mUserDetailsService).passwordEncoder(mPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()// 由于使用的是JWT，我们这里不需要csrf
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// 基于token，所以不需要session
                .and()
                .authorizeRequests()
                // 允许对于网站静态资源的无授权访问
                .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                ).permitAll()
                .antMatchers("/user/**").permitAll()
                .anyRequest().authenticated()
                .and().headers().cacheControl();// 禁用缓存
        httpSecurity.exceptionHandling()
                .authenticationEntryPoint(mEntryPointUnauthorizedHandler)
                .accessDeniedHandler(mRestAccessDeniedHandler);
        httpSecurity.addFilterBefore(mJwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 自定401返回值
     *
     * @author hackyo
     * Created on 2017/12/9 20:10.
     */
    @Component
    public static class EntryPointUnauthorizedHandler implements AuthenticationEntryPoint {

        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {
            ResponseUtils.responseResult(response, ResultGenerator.genFailResult(e.getMessage()).setCode(ResultCode.UNAUTHORIZED));
        }

    }

    /**
     * 自定403返回值
     *
     * @author hackyo
     * Created on 2017/12/9 20:10.
     */
    @Component
    public static class RestAccessDeniedHandler implements AccessDeniedHandler {

        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) {
            ResponseUtils.responseResult(response, ResultGenerator.genFailResult(e.getMessage()).setCode(ResultCode.ACCESS_DENIED));
        }

    }

    @Component
    public static class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

        @Override
        public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
            ResponseUtils.responseResult(response, ResultGenerator.genFailResult(e.getMessage()).setCode(ResultCode.UNAUTHORIZED));
        }
    }
}
