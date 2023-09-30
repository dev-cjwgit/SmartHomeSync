package com.cjw.smarthomesync.common.config;

import com.cjw.smarthomesync.common.domain.ResponseDto;
import com.cjw.smarthomesync.common.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
                , t.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto<String>> handler(MethodArgumentNotValidException t) {
        BaseException baseException = new BaseException();
        List<ObjectError> messageList;
        if (t != null)
            messageList = t.getBindingResult().getAllErrors();
        else
            messageList = ((org.springframework.validation.BindingResult) t).getAllErrors();

        for (ObjectError objectError : messageList) {
            String validationMessage = objectError.getDefaultMessage();
            baseException.appendMessage(validationMessage);
        }

        return new ResponseEntity<>(
                ResponseDto.<String>builder()
                        .result(false)
                        .message("요청 값이 올바르지 않습니다.")
                        .size(messageList.size())
                        .data(messageList.stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList()))
                        .build()
                , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ResponseDto<?>> handler(Throwable t) {
        t.printStackTrace();

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