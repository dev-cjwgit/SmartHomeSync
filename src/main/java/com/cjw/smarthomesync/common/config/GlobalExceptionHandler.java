package com.cjw.smarthomesync.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<?> handler(Throwable t) {
        log.error(t.getCause().toString());

        return new ResponseEntity<Object>(new HashMap<String, Object>() {{
            put("result", false);
            put("msg", "알 수 없는 예외입니다.");
        }}, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}