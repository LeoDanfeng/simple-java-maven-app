package com.mycompany.app.controller;

import com.mycompany.app.entity.mysql.SysUser;
import com.mycompany.app.exception.AppException;
import com.mycompany.app.security.config.JwtComponentConfig;
import com.mycompany.app.security.service.JwtManageService;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class LoginController {

    @Resource
    private JwtManageService jwtManageService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/login")
    public void login(@RequestBody SysUser user, HttpServletResponse response) throws IOException {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            throw new AppException("Already login. Please logout first to reLogin");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getAccount(), user.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtManageService.generateToken(authentication);
            response.addHeader(JwtComponentConfig.AUTHORIZATION_HEAD, JwtComponentConfig.TOKEN_PREFIX + token);
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {
        String token = request.getHeader(JwtComponentConfig.AUTHORIZATION_HEAD);
        JWTClaimsSet jwtClaimsSet = jwtManageService.getJWTClaimsSet(token);
        stringRedisTemplate.delete(jwtClaimsSet.getJWTID());
    }
}
