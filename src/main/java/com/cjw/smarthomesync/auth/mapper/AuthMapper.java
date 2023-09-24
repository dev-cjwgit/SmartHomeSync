package com.cjw.smarthomesync.auth.mapper;

import com.cjw.smarthomesync.auth.domain.AuthDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface AuthMapper {
    Optional<AuthDTO> findUserByEmail(String email) throws Exception;

    Optional<AuthDTO> findUserByUserId(String id) throws Exception;

    Optional<AuthDTO> findUserByUid(Long uid);

    Optional<AuthDTO> findUserById(String id) throws Exception;
}
