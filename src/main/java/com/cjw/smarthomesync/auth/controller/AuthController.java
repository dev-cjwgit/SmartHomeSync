package com.cjw.smarthomesync.auth.controller;

import com.cjw.smarthomesync.auth.domain.request.LoginVo;
import com.cjw.smarthomesync.auth.domain.request.SignupVo;
import com.cjw.smarthomesync.auth.domain.response.JwtTokenVo;
import com.cjw.smarthomesync.auth.service.AuthService;
import com.cjw.smarthomesync.common.annotation.ValidationGroups;
import com.cjw.smarthomesync.common.domain.AuthEntity;
import com.cjw.smarthomesync.common.domain.ResponseDto;
import com.cjw.smarthomesync.common.exception.BaseException;
import com.cjw.smarthomesync.common.exception.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<AuthEntity>> signup(@RequestBody @Validated(ValidationGroups.signup.class) SignupVo signupVo) {
        log.info("회원 가입 기능 수행");
        authService.signup(signupVo);

        return ResponseEntity.ok(ResponseDto.<AuthEntity>builder()
                .result(true)
                .message("회원 가입 완료")
                .size(0)
                .data(Collections.emptyList())
                .build());
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<JwtTokenVo>> login(@RequestBody @Validated(ValidationGroups.login.class) LoginVo loginVo) {
        log.info("로그인 기능 수행");
        JwtTokenVo jwtTokenVo = authService.login(loginVo);

        return ResponseEntity.ok(ResponseDto.<JwtTokenVo>builder()
                .result(true)
                .message("로그인 완료")
                .size(1)
                .data(Collections.singletonList(jwtTokenVo))
                .build());
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDto<?>> logout(final Authentication authentication) {
        log.info("로그아웃 기능 수행");
        AuthEntity authEntity = (AuthEntity) authentication.getPrincipal();
        boolean result = authService.logout(authEntity.getUid());
        return ResponseEntity.ok(ResponseDto.<String>builder()
                .result(result)
                .message(result ? "로그아웃 성공" : "로그아웃 실패")
                .size(0)
                .data(Collections.emptyList())
                .build());
    }

    @PostMapping("/refresh")
    public ResponseEntity<ResponseDto<JwtTokenVo>> refreshToken(@RequestBody Long uid, HttpServletRequest request) {
        log.info("리프레시 토큰 재발긍 수행");
        String token = request.getHeader("refresh-token");
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
                                            .accessToken(token)
                                            .build()
                            )
                    )
                    .build());
        }

        throw new BaseException(ErrorMessage.UNDEFINED_EXCEPTION);
    }

}
