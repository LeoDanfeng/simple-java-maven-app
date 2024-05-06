package com.mycompany.app.aop;

import com.mycompany.app.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class LogAspect {

    @Resource
    private RedisTemplate redisTemplate;

    @Pointcut("@annotation(com.mycompany.app.aop.LogEnhance)")
    public void logEnhance() {

    }

    @Pointcut("execution(* *..*StarterApplication.get*(..))")
    public void queryByCache() {

    }

    @Pointcut("execution(* *..*StarterApplication.update*(..))")
    public void updateCache() {

    }

    @Around("queryByCache()")
    public Object enhanceQuery(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        Object[] parameterValues = joinPoint.getArgs();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        Class<?> returnType = method.getReturnType();
        if (returnType == null) {
            joinPoint.proceed();
            return null;
        }
        StringBuilder keyBuilder = new StringBuilder();
        keyBuilder.append(returnType.getSimpleName());
        keyBuilder.append("::");
        String[] parameterNames = methodSignature.getParameterNames();
        for (int i = 0; i < parameterNames.length; i++) {
            if (parameterNames[i].equals("id")) {
                keyBuilder.append(parameterValues[i]);
                break;
            }
        }
        String key = keyBuilder.toString();
        Object result = redisTemplate.opsForValue().get(key);
        if (result != null) {
            return result;
        }
        result = joinPoint.proceed();
        redisTemplate.opsForValue().set(key, result);
        return result;
    }

    @AfterReturning(value = "updateCache()", returning = "result")
    public Object enhanceUpdate(JoinPoint joinPoint, final Object result) {
        try {
            Object[] parameterValues = joinPoint.getArgs();
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            Class<?> returnType = method.getReturnType();
            if (returnType == null) {
                return null;
            }
            StringBuilder keyBuilder = new StringBuilder();
            String[] parameterNames = signature.getParameterNames();
            keyBuilder.append(returnType.getSimpleName());
            keyBuilder.append("::");
            Method getId = returnType.getMethod("getId");
            Long id = (Long) getId.invoke(result);
            keyBuilder.append(id);
            String key = keyBuilder.toString();
            redisTemplate.opsForValue().set(key, result);
        } catch (NoSuchMethodException e) {
            throw new AppException("目标(" + result + ")必须包含getId方法", e);
        } catch (InvocationTargetException e) {
            throw new AppException("目标(" + result + ")调用getId方法出现异常", e);
        } catch (IllegalAccessException e) {
            throw new AppException("目标(" + result + ")getId方法无法访问", e);
        }
        return result;
    }


}
