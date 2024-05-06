package com.mycompany.app.security;

import com.mycompany.app.aop.LogEnhance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class AppEntryPoint implements AuthenticationEntryPoint {

    @Override
    @LogEnhance
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.getWriter().write("你的访问请求被拒绝，请先登录！");
    }

}
