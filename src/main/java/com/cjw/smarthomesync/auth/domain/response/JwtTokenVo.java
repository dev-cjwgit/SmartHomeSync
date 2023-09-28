package com.cjw.smarthomesync.auth.domain.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtTokenVo {
    private String accessToken;
    private String refreshToken;
}
