package com.cjw.smarthomesync.auth.controller;

import com.cjw.smarthomesync.auth.domain.request.AuthDto;
import com.cjw.smarthomesync.auth.domain.request.LoginVo;
import com.cjw.smarthomesync.auth.domain.response.JwtTokenVo;
import com.cjw.smarthomesync.auth.service.AuthService;
import com.cjw.smarthomesync.common.domain.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<AuthDto>> signup(@RequestBody AuthDto authDto) {
        authService.signup(authDto);

        return ResponseEntity.ok(ResponseDto.<AuthDto>builder()
                .result(true)
                .message("회원 가입 완료")
                .size(0)
                .data(Collections.emptyList())
                .build());
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<JwtTokenVo>> login(@RequestBody LoginVo loginVo) {
        JwtTokenVo jwtTokenVo = authService.login(loginVo);

        return ResponseEntity.ok(ResponseDto.<JwtTokenVo>builder()
                .result(true)
                .message("회원 가입 완료")
                .size(1)
                .data(Collections.singletonList(jwtTokenVo))
                .build());
    }
}
