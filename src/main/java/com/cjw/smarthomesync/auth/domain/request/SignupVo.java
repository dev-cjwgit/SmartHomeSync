package com.cjw.smarthomesync.auth.domain.request;

import com.cjw.smarthomesync.common.annotation.ValidationGroups;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SignupVo {
    @Hidden
    private Long uid;

    @NotBlank(groups = {ValidationGroups.signup.class}, message = "이메일은 공백일 수 없습니다.")
    @Email(groups = {ValidationGroups.signup.class}, message = "이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(groups = {ValidationGroups.signup.class}, message = "비밀 번호는 공백일 수 없습니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?=\\S+$).{8,30}", groups = {ValidationGroups.signup.class}, message = "비밀 번호는 8~30자 특수 문자 대,소문자 그리고 숫자가 포함 되어야 합니다.")
    private String password;

    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9-_]{2,20}$", groups = {ValidationGroups.signup.class}, message = "이름은 특수 문자를 제외한 2~20자리 여야 합니다.")
    private String name;

    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9-_]{2,10}$", groups = {ValidationGroups.signup.class}, message = "닉네임은 특수 문자를 제외한 2~10자리 여야 합니다.")
    private String nickname;

    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", groups = {ValidationGroups.signup.class}, message = "핸드폰 번호의 양식과 맞지 않습니다. 01X-XXX(X)-XXXX")
    private String phoneNumber;
}
