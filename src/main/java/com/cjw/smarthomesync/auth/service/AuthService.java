package com.cjw.smarthomesync.auth.service;

import com.cjw.smarthomesync.auth.domain.request.LoginVo;
import com.cjw.smarthomesync.auth.domain.request.SignupVo;
import com.cjw.smarthomesync.auth.domain.response.JwtTokenVo;

public interface AuthService {
    void signup(SignupVo authEntity);

    boolean logout(Long uid);

    JwtTokenVo login(LoginVo loginDto);

    String refreshToken(Long uid, String token);
}
