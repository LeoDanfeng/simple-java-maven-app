package com.mycompany.app.security.filter;

import com.mycompany.app.security.config.JwtComponentConfig;
import com.mycompany.app.security.service.JwtManageService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtPreAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    private JwtManageService jwtManageService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = request.getHeader(JwtComponentConfig.AUTHORIZATION_HEAD);
            if (StringUtils.hasText(token) && token.startsWith(JwtComponentConfig.TOKEN_PREFIX)) {
                token = token.substring(7);
                Authentication authentication = jwtManageService.getAuthentication(token);
                if (authentication != null) {
                    String savedToken = stringRedisTemplate.opsForValue().get(authentication.getName());
                    if (savedToken != null) {
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
        } finally {
            filterChain.doFilter(request, response);
        }
    }
}
