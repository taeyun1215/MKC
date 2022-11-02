package com.mck.web.dto;

import com.mck.domain.user.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.*;

@Getter
@Setter
public class UserSignupDto {

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

    private String confirmPassword;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    @Size(max = 20, message = "이름은 8자 이하로 입력해주세요.")
    private String name;

    @Pattern(
            regexp = "/^\\d{3}-\\d{3,4}-\\d{4}$/",
            message = "핸드폰 번호 형식을 맞춰주세요."
    )
    private String phone;

    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .phone(phone)
                .build();
    }

}
