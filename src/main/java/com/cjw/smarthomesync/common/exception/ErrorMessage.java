package com.cjw.smarthomesync.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorMessage {
    ACCESS_TOKEN_EXPIRE(1000, "토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED),
    ACCESS_TOKEN_INVALID(1001, "토큰이 잘못되었습니다.", HttpStatus.UNAUTHORIZED),
    ACCESS_TOKEN_NOT_LOAD(1002, "토큰을 불러오지 못하였습니다.", HttpStatus.UNAUTHORIZED),
    ACCESS_TOKEN_INVALID_STRUCT(1010, "토큰이 구조가 잘못되었습니다.", HttpStatus.UNAUTHORIZED),
    ACCESS_TOKEN_INVALID_HEADER(1011, "토큰 해더가 손상되었습니다.", HttpStatus.UNAUTHORIZED),
    ACCESS_TOKEN_INVALID_PAYLOADS(1012, "토큰 정보가 손상되었습니다.", HttpStatus.UNAUTHORIZED),
    ACCESS_TOKEN_INVALID_SIGNATURE(1013, "토큰이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED),
    ACCESS_TOKEN_EMPTY(1014, "토큰이 입력되지 않았습니다.", HttpStatus.BAD_REQUEST),
    UNDEFINED_EXCEPTION(0, "정의 되지 않은 오류 입니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    EXIST_EMAIL(2000, "이미 존재하는 이메일입니다.", HttpStatus.BAD_REQUEST);

    private final Integer code;
    private final String message;
    private final HttpStatus httpStatus;
}
