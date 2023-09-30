package com.cjw.smarthomesync.auth.domain.request;

import com.cjw.smarthomesync.common.annotation.ValidationGroups;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginVo {
    @NotBlank(groups = {ValidationGroups.login.class}, message = "이메일은 공백일 수 없습니다.")
    @Email(groups = {ValidationGroups.login.class}, message = "이메일 형식이 아닙니다.")
    private String email;


    @NotBlank(groups = {ValidationGroups.login.class}, message = "비밀 번호는 공백일 수 없습니다.")
    @Size(min = 8, max = 30, groups = {ValidationGroups.login.class}, message = "비밀 번호는 8글자 이상 30글자 이하입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?=\\S+$).{8,30}", groups = {ValidationGroups.login.class}, message = "비밀 번호는 8~30자 특수 문자 대,소문자 그리고 숫자가 포함 되어야 합니다.")
    private String password;
}
