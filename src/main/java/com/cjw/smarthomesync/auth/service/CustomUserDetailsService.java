package com.cjw.smarthomesync.auth.service;

import com.cjw.smarthomesync.common.domain.AuthEntity;
import com.cjw.smarthomesync.auth.mapper.AuthMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final AuthMapper authMapper;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return authMapper.findAccountByUid(Long.valueOf(userId))
                .map(this::addAuthorities)
                .orElseThrow(() -> new RuntimeException(userId + "> 찾을 수 없습니다."));
    }

    private AuthEntity addAuthorities(AuthEntity userDto) {
        userDto.setAuthorities(List.of(new SimpleGrantedAuthority(userDto.getRole())));

        return userDto;
    }
}