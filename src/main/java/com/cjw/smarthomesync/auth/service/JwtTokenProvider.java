package com.cjw.smarthomesync.auth.service;

import com.cjw.smarthomesync.auth.mapper.AuthMapper;
import com.cjw.smarthomesync.common.exception.BaseException;
import com.cjw.smarthomesync.common.exception.ErrorMessage;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final UserDetailsService userDetailsService;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.token-validity-in-minutes}")
    private long tokenValidMinutes;

    @Value("${jwt.refresh-validity-in-minutes}")
    private long refreshValidMinutes;

    private final AuthMapper authMapper;

    public String create(Long uid, List<String> roles, long expire) {
        Claims claims = Jwts.claims().setSubject(Long.toString(uid));
        claims.put("roles", roles);
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expire)) // 토큰 만료일 설정
                .signWith(SignatureAlgorithm.HS256, (secretKey + authMapper.getSalt(uid)).getBytes()) // 암호화
                .compact();
    }

    // JWT 토큰 생성
    public String createToken(Long uid, List<String> roles) {
//        return create(uid, roles, 1000 * 5);
        return create(uid, roles, 1000 * 10 * tokenValidMinutes);
    }

    public String createRefresh(Long uid, List<String> roles) {
//        return create(uid, roles, 1000 * 10 * 60);
        return create(uid, roles, 1000 * 10 * refreshValidMinutes);
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserId(token));

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 유저 이름 추출
    public String getUserId(String token) {
        return Jwts.parser()
                .setSigningKey((secretKey + authMapper.getSalt(getUid(token))).getBytes())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Request header에서 token 꺼내옴
    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        // 가져온 Authorization Header 가 문자열이고, Bearer 로 시작해야 가져옴
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }

        return null;
    }

    // JWT 토큰 유효성 체크
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey((secretKey + authMapper.getSalt(getUid(token))).getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (SecurityException | MalformedJwtException | IllegalArgumentException | ExpiredJwtException |
                 UnsupportedJwtException exception) {
            log.error(exception.getMessage());
        }

        return false;
    }

    private Long getUid(String token) {
        try {
            if (token.chars().filter(c -> c == '.').count() != 2)
                throw new BaseException(ErrorMessage.ACCESS_TOKEN_INVALID);

            Map<?, ?> map;
            map = new ObjectMapper().readValue(Base64.getDecoder().decode(token.split("\\.")[1]), Map.class);
            if (map.get("sub") == null)
                throw new BaseException(ErrorMessage.ACCESS_TOKEN_INVALID_PAYLOADS);

            return Long.parseLong(map.get("sub").toString());
        } catch (JsonParseException ex) {
            throw new BaseException(ErrorMessage.ACCESS_TOKEN_INVALID_STRUCT);
        } catch (IOException e) {
            throw new BaseException(ErrorMessage.UNDEFINED_EXCEPTION);
        }
    }
}
