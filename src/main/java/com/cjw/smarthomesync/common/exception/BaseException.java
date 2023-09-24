package com.cjw.smarthomesync.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BaseException extends RuntimeException {
    private Integer errorCode;

    private List<String> errorMessage;

    private List<String> errorTrace;

    private HttpStatus httpStatus;

    public BaseException(ErrorMessage errorMessage) {
        this.errorCode = errorMessage.getCode();
        this.errorMessage = new ArrayList<>();
        this.errorMessage.add(errorMessage.getMessage());
        this.httpStatus = errorMessage.getHttpStatus();
        errorTrace = new ArrayList<>();
    }
}