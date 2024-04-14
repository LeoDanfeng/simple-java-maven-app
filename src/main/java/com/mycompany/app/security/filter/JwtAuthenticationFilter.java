package com.mycompany.app.security.filter;

import com.mycompany.app.security.config.JwtComponentConfig;
import com.mycompany.app.service.JwtLoginService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtLoginService jwtLoginService;

    public JwtAuthenticationFilter(JwtLoginService jwtLoginService) {
        this.jwtLoginService = jwtLoginService;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = request.getHeader(JwtComponentConfig.AUTHORIZATION_HEAD);
            if (StringUtils.hasText(token) && token.startsWith(JwtComponentConfig.TOKEN_PREFIX)) {
                token = token.substring(7);
                Authentication authentication = jwtLoginService.getAuthentication(token);
                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    String refreshToken = jwtLoginService.refreshToken(token);
                    if (!token.equals(refreshToken)) {
                        response.addHeader("Access-Control-Expose-Headers","token");
                        response.addHeader("token",refreshToken);
                    }
                }
            }
        } finally {
            filterChain.doFilter(request, response);
        }
    }
}
