package com.cjw.smarthomesync.common.config;

import com.cjw.smarthomesync.common.domain.ResponseDto;
import com.cjw.smarthomesync.common.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ResponseDto<?>> handler(BaseException t) {
        List<String> errorList = t.getErrorMessage();
        return new ResponseEntity<>(
                ResponseDto.<String>builder()
                        .result(false)
                        .message("요청에 응답을 실패하였습니다.")
                        .size(errorList.size())
                        .data(errorList)
                        .build()
                , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ResponseDto<?>> handler(Throwable t) {
        log.error(t.getCause().toString());

        return new ResponseEntity<>(
                ResponseDto.builder()
                        .result(false)
                        .message("알 수 없는 예외 입니다.")
                        .size(0)
                        .data(Collections.emptyList())
                        .build()
                , HttpStatus.INTERNAL_SERVER_ERROR);

    }
}