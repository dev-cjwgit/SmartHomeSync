package com.cjw.smarthomesync.auth.service;

import com.cjw.smarthomesync.auth.domain.request.AuthDto;
import com.cjw.smarthomesync.auth.mapper.AuthMapper;
import com.cjw.smarthomesync.common.exception.BaseException;
import com.cjw.smarthomesync.common.exception.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthMapper authMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void signup(AuthDto authDto) throws Exception {
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
}
