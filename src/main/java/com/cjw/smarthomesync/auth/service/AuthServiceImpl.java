package com.cjw.smarthomesync.auth.service;

import com.cjw.smarthomesync.auth.domain.request.AuthDto;
import com.cjw.smarthomesync.auth.domain.request.LoginVo;
import com.cjw.smarthomesync.auth.domain.response.JwtTokenVo;
import com.cjw.smarthomesync.auth.mapper.AuthMapper;
import com.cjw.smarthomesync.common.exception.BaseException;
import com.cjw.smarthomesync.common.exception.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthMapper authMapper;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional
    public void signup(AuthDto authDto) {
        if (authMapper.findAccountByEmail(authDto.getEmail()).isPresent())
            // 이미 존재 하는 이메일
            throw new BaseException(ErrorMessage.EXIST_EMAIL);

        // 비밀 번호 암호화
        authDto.setPassword(passwordEncoder.encode(authDto.getPassword()));

        // 회원 정보 DB 등록
        authMapper.signup(authDto);

        // salt 값 설정을 위한 현재 서버 시간 파라미터 생성
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        // salt 생성
        String salt = authDto.getUid().toString() + calendar.getTime();

        // 암호화 진행
        salt = (BCrypt.hashpw(salt, BCrypt.gensalt()));

        // salt 값 DB 반영
        authMapper.setSalt(authDto.getUid(), salt);
    }

    @Override
    @Transactional
    public JwtTokenVo login(LoginVo loginVo) {
        // 로그인 정보로 부터 DB 에서 계정 정보를 로딩
        AuthDto account = authMapper.findAccountByEmail(loginVo.getEmail())
                .orElseThrow(() -> new BaseException(ErrorMessage.EXIST_EMAIL));

        // 비밀 번호가 맞지 않으면
        if (!passwordEncoder.matches(loginVo.getPassword(), account.getPassword())) {
            throw new BaseException(ErrorMessage.NOT_MATCH_PASSWORD);
        }

        // 토근 생성
        String accessToken = jwtTokenProvider.createToken(account.getUid(), Collections.singletonList(account.getRole()));
        String refreshToken = jwtTokenProvider.createRefresh(account.getUid(), Collections.singletonList(account.getRole()));
        account.setRefreshToken(refreshToken);

        // 서버에 리프레시 토큰만 저장
        authMapper.setRefreshToken(account);

        // 데이터 반환
        return JwtTokenVo.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
