package com.cjw.smarthomesync.auth.mapper;

import com.cjw.smarthomesync.auth.domain.request.AuthDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface AuthMapper {
    Optional<AuthDto> findAccountByUid(Long uid);

    Optional<AuthDto> findAccountByEmail(String email);

    void setSalt(@Param(value = "uid") Long uid, @Param(value = "salt") String salt);

    String getSalt(Long uid);

    void setRefreshToken(@Param("uid") Long uid, @Param("refresh_token") String refreshToken);

    String getRefreshToken(@Param("uid") Long uid);

    void signup(AuthDto authDto);
}
