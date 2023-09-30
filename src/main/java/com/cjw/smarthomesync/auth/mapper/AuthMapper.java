package com.cjw.smarthomesync.auth.mapper;

import com.cjw.smarthomesync.auth.domain.request.SignupVo;
import com.cjw.smarthomesync.common.domain.AuthEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface AuthMapper {
    Optional<AuthEntity> findAccountByUid(Long uid);

    Optional<AuthEntity> findAccountByEmail(String email);

    void setSalt(@Param(value = "uid") Long uid, @Param(value = "salt") String salt);

    String getSalt(Long uid);

    void setRefreshToken(@Param("uid") Long uid, @Param("refresh_token") String refreshToken);

    String getRefreshToken(@Param("uid") Long uid);

    void signup(SignupVo signupVo);
}
