package com.mycompany.app.security.filter;

import com.mycompany.app.security.config.JwtComponentConfig;
import com.mycompany.app.security.service.JwtManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: 罗丹枫
 * @Description: token登录认证过滤器
 * @CreatedAt: 2024/7/11 22:22
 */

//@Component
@Slf4j
public class JwtPreAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    private JwtManageService jwtManageService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String originalToken = request.getHeader(JwtComponentConfig.AUTHORIZATION_HEAD);
        if (StringUtils.hasText(originalToken) && originalToken.startsWith(JwtComponentConfig.TOKEN_PREFIX)) {
            String token = originalToken.substring(7);
            // 验证token并保存请求用户信息
            Authentication authentication = jwtManageService.getAuthentication(token);
            if (authentication != null) {
                String savedToken = stringRedisTemplate.opsForValue().get(authentication.getName());
                if (originalToken.equals(savedToken)) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
