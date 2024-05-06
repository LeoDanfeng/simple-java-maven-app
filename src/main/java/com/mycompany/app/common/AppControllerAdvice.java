package com.mycompany.app.common;

import com.mycompany.app.exception.AppException;
import com.mycompany.app.exception.ThirdPartyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;

@RestControllerAdvice
@Slf4j
public class AppControllerAdvice {

    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity handleException(Throwable t) {
        log.error("NotHandledException Occurred");
        log.error("异常Class({}),异常Message: {},异常Cause: {}", t.getClass(), t.getMessage(), t.getCause());
        log.error("异常堆栈信息：{}", getErrorStack(t));
        return new ResponseEntity("服务器内部异常:" + t.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {AppException.class})
    public ResponseEntity handleAppException(AppException e) {
        log.error("AppException Occurred");
        log.error("异常Message({}),异常Cause: {}", e.getMessage(), e.getCause());
        log.error("异常堆栈信息：{}", getErrorStack(e));
        return new ResponseEntity("服务器内部异常：" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {ThirdPartyException.class})
    public ResponseEntity handleThirdPartyException(AppException e) {
        log.error("ThirdPartyException Occurred");
        log.error("异常Message({}),异常Cause: {}", e.getMessage(), e.getCause());
        log.error("异常堆栈信息：{}", getErrorStack(e));
        return new ResponseEntity("服务器内部异常：" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getErrorStack(Throwable t) {
        StringWriter stringWriter = new StringWriter();
        t.printStackTrace(new PrintWriter(stringWriter, true));
        return stringWriter.toString();
    }

}
