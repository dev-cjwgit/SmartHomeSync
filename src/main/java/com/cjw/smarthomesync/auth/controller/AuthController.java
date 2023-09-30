package com.cjw.smarthomesync.auth.controller;

import com.cjw.smarthomesync.auth.domain.request.AuthDto;
import com.cjw.smarthomesync.auth.domain.request.LoginVo;
import com.cjw.smarthomesync.auth.domain.response.JwtTokenVo;
import com.cjw.smarthomesync.auth.service.AuthService;
import com.cjw.smarthomesync.common.domain.ResponseDto;
import com.cjw.smarthomesync.common.exception.BaseException;
import com.cjw.smarthomesync.common.exception.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
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

    @PostMapping("/refresh")
    public ResponseEntity<ResponseDto<JwtTokenVo>> refreshToken(@RequestBody Long uid, HttpServletRequest request, @RequestParam String refreshToken) {
//        String token = request.getHeader("refresh-token");
        String token = refreshToken;
        String result = authService.refreshToken(uid, token);

        if (result != null && !result.isEmpty()) {
            // 발급 성공
            return ResponseEntity.ok(ResponseDto.<JwtTokenVo>builder()
                    .result(true)
                    .message("엑세스 토큰 발급 완료")
                    .size(1)
                    .data(
                            Collections.singletonList(
                                    JwtTokenVo.builder()
                                            .refreshToken(token)
                                            .build()
                            )
                    )
                    .build());
        }

        throw new BaseException(ErrorMessage.UNDEFINED_EXCEPTION);
    }

}
