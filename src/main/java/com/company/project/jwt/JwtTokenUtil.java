package com.company.project.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.*;

/**
 * JWT工具类
 *
 * @author hackyo
 * Created on 2017/12/8 9:20.
 */
@Component
public class JwtTokenUtil implements Serializable {

    public static final byte DEFAULT_USER_ROLE_ID = 3;
    //说是权限一定要ROLE_开头
    public static final String SUPER_ADMIN = "ROLE_super", ADMIN = "ROLE_admin", USER = "ROLE_user", NONE = "ROLE_none";

    //public static final String KEY_TOKEN_USER = "user", KEY_TOKEN_TIME = "time";
    private static final long EXPIRATION_LIMIT = 30L * 24 * 60 * 60 * 1000;

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

    /**
     * 密钥
     */
    @Value("${jwt.secret}")
    private String mJwtSecret;

    @Value("${jwt.token.key}")
    private String mTokenKey;

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, mJwtSecret).compact();
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(mJwtSecret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    public String getToken(HttpServletRequest request) {
        String token = null;
        if (request != null) token = request.getHeader(mTokenKey);
        return token;
    }

    public String getIdFromRequest(HttpServletRequest request) {
        final String token = getToken(request);
        if (token != null)
            return getIdFromToken(token);
        return null;
    }

    /**
     * 生成令牌
     *
     * @param userDetails 用户
     * @return 令牌
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>(4);
        claims.put(Claims.ISSUER, userDetails.getUsername());
        claims.put(Claims.ISSUED_AT, new Date());
        claims.put(Claims.SUBJECT, userDetails.getPassword());
        claims.put(Claims.EXPIRATION, new Date(System.currentTimeMillis() + EXPIRATION_LIMIT));
        return generateToken(claims);
    }

    /**
     * 从令牌中获取用户id
     *
     * @param token 令牌
     * @return 用户名
     */
    public String getIdFromToken(String token) {
        String id = null;
        try {
            Claims claims = getClaimsFromToken(token);
            Object issuer = claims.getIssuer();
            if (issuer != null) id = String.valueOf(issuer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * 判断令牌是否过期
     *
     * @param token 令牌
     * @return 是否过期
     */
    public Boolean isTokenExpired(String token, String password) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            final boolean valid = expiration.after(new Date()) && claims.getSubject().equals(password);
            if (valid) {//是否有效，有效则把过期时间往后延，不知道这个操作有没有效果
                claims.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_LIMIT));
            }
            return !valid;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    ///**
    // * 刷新令牌
    // *
    // * @param token 原令牌
    // * @return 新令牌
    // */
    //public String refreshToken(String token) {
    //    String refreshedToken;
    //    try {
    //        Claims claims = getClaimsFromToken(token);
    //        claims.put(KEY_TOKEN_TIME, new Date());
    //        refreshedToken = generateToken(claims);
    //    } catch (Exception e) {
    //        refreshedToken = null;
    //    }
    //    return refreshedToken;
    //}

    /**
     * 验证令牌
     *
     * @param token       令牌
     * @param userDetails 用户
     * @return 是否有效
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        try {
            JwtUserDetails user = (JwtUserDetails) userDetails;
            String username = getIdFromToken(token);
            return (username.equals(user.getUsername()) && !isTokenExpired(token, userDetails.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
