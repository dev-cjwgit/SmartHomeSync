package com.cjw.smarthomesync.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorMessage {
    VALIDATION_FAIL_EXCEPTION(-1, "입력 값의 조건이 잘못 되었습니다.", HttpStatus.BAD_REQUEST),

    ACCESS_TOKEN_EXPIRE(1000, "토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED),
    ACCESS_TOKEN_INVALID(1001, "토큰이 잘못되었습니다.", HttpStatus.UNAUTHORIZED),
    ACCESS_TOKEN_NOT_LOAD(1002, "토큰을 불러오지 못하였습니다.", HttpStatus.UNAUTHORIZED),
    ACCESS_TOKEN_INVALID_STRUCT(1010, "토큰이 구조가 잘못되었습니다.", HttpStatus.UNAUTHORIZED),
    ACCESS_TOKEN_INVALID_HEADER(1011, "토큰 해더가 손상되었습니다.", HttpStatus.UNAUTHORIZED),
    ACCESS_TOKEN_INVALID_PAYLOADS(1012, "토큰 정보가 손상되었습니다.", HttpStatus.UNAUTHORIZED),
    ACCESS_TOKEN_INVALID_SIGNATURE(1013, "토큰이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED),

    REFRESH_TOKEN_EXPIRE(900, "리프세리 토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED),
    REFRESH_TOKEN_NOT_MATCH(901, "리프레시 토큰이 일치하지 않습니다.", HttpStatus.UNAUTHORIZED),

    NOT_USER_INFO_MATCH(210, "유저 정보가 옳바르지 않습니다.", HttpStatus.BAD_REQUEST),

    NOT_USER_INFO(300, "유저정보가 존재하지 않습니다.", HttpStatus.BAD_REQUEST),

    ACCESS_TOKEN_EMPTY(1014, "토큰이 입력되지 않았습니다.", HttpStatus.BAD_REQUEST),
    EXIST_EMAIL(2000, "이미 존재 하는 이메일입니다.", HttpStatus.BAD_REQUEST),
    NOT_EXIST_EMAIL(2001, "이미 존재 하지 않는 이메일입니다.", HttpStatus.BAD_REQUEST),
    NOT_MATCH_PASSWORD(2002, "비밀번호가 틀렸습니다.", HttpStatus.BAD_REQUEST),

    NOT_LOGIN(2003, "로그인이 되어 있지 않습니다.", HttpStatus.BAD_REQUEST),


    UNDEFINED_EXCEPTION(0, "정의 되지 않은 오류 입니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final Integer code;
    private final String message;
    private final HttpStatus httpStatus;
}
