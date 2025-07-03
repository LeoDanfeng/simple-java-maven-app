package com.mycompany.app.aop;

import com.mycompany.app.dao.redis.RequestStatisticRepository;
import com.mycompany.app.entity.redis.RequestStatistic;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
public class LogAspect {

    @Resource
    private RequestStatisticRepository requestStatisticRepository;

    @Pointcut("execution(* *..*Controller.*(..))")
    public void beforeRequest() {

    }

    /**
     * @Author: 罗丹枫
     * @Description: 统计接口的调用信息
     * @CreatedAt: 2024/7/11 21:49
     * @Params: [joinPoint]  AOP切点
     * @Return: void
     */

    @Before("beforeRequest()")
    public void requestStatistics(JoinPoint joinPoint) {
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
//        if ("anonymousUser".equals(currentUser)) {
//            return;
//        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        RequestStatistic rs = new RequestStatistic();
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.isEmpty() || ipAddress.equalsIgnoreCase("unknown")) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || ipAddress.equalsIgnoreCase("unknown")) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || ipAddress.equalsIgnoreCase("unknown")) {
            ipAddress = request.getHeader("HTTP-CLIENT-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || ipAddress.equalsIgnoreCase("unknown")) {
            ipAddress = request.getHeader("HTTP-X-FORWARDED-FOR");
        }
        if (ipAddress == null || ipAddress.isEmpty() || ipAddress.equalsIgnoreCase("unknown")) {
            ipAddress = request.getRemoteAddr();
        }
        rs.setAccessIp(ipAddress);
        rs.setRemoteAddr(request.getRemoteAddr());
        rs.setAccessUri(request.getRequestURI());
        rs.setAccessUser(currentUser);
        rs.setAccessTime(LocalDateTime.now());
        Object[] args = joinPoint.getArgs();
        StringBuilder builder = new StringBuilder();
        for (Object o : args) {
            builder.append(o.toString());
            builder.append(";");
        }
        rs.setArgs(builder.toString());
        requestStatisticRepository.save(rs);
    }
}
