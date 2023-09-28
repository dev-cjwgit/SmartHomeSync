package com.cjw.smarthomesync.auth.domain.request;

import lombok.Data;

@Data
public class LoginVo {
    private String email;
    private String password;
}
