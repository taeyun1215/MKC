package com.mck.web.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class UserLoginDto {

    @Email
    @NotNull(message = "이메일은 필수 입력 값입니다.")
    private String email;

    @Size(min = 8, max = 16)
    @Pattern(
            regexp = "/^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,16}$/",
            message = "비밀번호는 최소 8자 이상 16자 이하, 하나 이상의 영문, 숫자, 특수문자가 포함되어야 합니다."
    )
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;

}
