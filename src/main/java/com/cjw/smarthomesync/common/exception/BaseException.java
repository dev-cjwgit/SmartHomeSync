package com.cjw.smarthomesync.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class BaseException extends RuntimeException {
    private Integer errorCode;

    private List<String> errorMessage;

    private List<String> errorTrace;

    private HttpStatus httpStatus;

    public BaseException() {
        this.errorMessage = new ArrayList<>();
        errorTrace = new ArrayList<>();
    }

    public BaseException(ErrorMessage errorMessage) {
        this();
        this.errorCode = errorMessage.getCode();
        this.errorMessage.add(errorMessage.getMessage());
        this.httpStatus = errorMessage.getHttpStatus();
    }

    public void appendMessage(String message) {
        errorMessage.add(message);
    }
}