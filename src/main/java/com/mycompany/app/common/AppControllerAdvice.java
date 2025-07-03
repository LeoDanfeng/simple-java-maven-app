package com.mycompany.app.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class AppControllerAdvice {

    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity<?> handleException(Throwable t) {
        log.error("Unhandled exception", t);
        return ResponseEntity.internalServerError().build();
    }

}
