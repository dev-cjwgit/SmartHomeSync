package com.cjw.smarthomesync.auth.service;

import com.cjw.smarthomesync.auth.domain.request.AuthDto;

public interface AuthService {
    void signup(AuthDto authDto) throws Exception;
}
