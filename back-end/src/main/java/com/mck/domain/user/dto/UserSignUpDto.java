package com.mck.domain.user.dto;

import com.mck.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor // 테스트 코드 작성용
public class UserSignUpDto {

    @Size(max = 12, message = "닉네임은 12자 이하로 입력해주세요.")
    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    private String nickname;

    @NotBlank
    @Pattern(regexp = "^[a-z]+[a-z0-9]{5,10}$",
            message = "아이디는 최소 5자 이상, 10자 이하여야 하고 영어로 시작하는 영어+숫자 조합으로 구성되어야 합니다")
    private String username;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$",
            message = "비밀번호는 최소 8자 이상, 하나 이상의 문자 및 숫자, 하나 이상의 특수문자가 포함되어야 합니다")
    private String password;

    @Size(min = 8, max = 16)
    @NotBlank(message = "비밀번호확인은 필수 입력 값입니다.")
    private String confirmPassword;

    @NotBlank
    @Email(message = "올바른 이메일 형식으로 입력해주세요")
    @Pattern(regexp = "^[a-z0-9]+@goldenplanet.co.kr$",
            message = "goldenplanet.co.kr 이메일만 사용 가능합니다")
    private String email;

    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .email(email)
                .emailVerified(false)
                .build();
    }

}