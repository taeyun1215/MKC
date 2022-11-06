package com.mck.web.dto;

import com.mck.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor // 테스트 코드 작성용
public class UserSignupDto {

    @Size(min = 5, max = 15, message = "아이디는 최소 5자 이상 15자 이하입니다.")
    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String userName;

    @Size(min = 8, max = 16)
    @Pattern(
            regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",
            message = "비밀번호는 최소 8자 이상 16자 이하, 하나 이상의 영문, 숫자, 특수문자가 포함되어야 합니다."
    )
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;

    @Size(min = 8, max = 16)
    @Pattern(
            regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",
            message = "비밀번호는 최소 8자 이상 16자 이하, 하나 이상의 영문, 숫자, 특수문자가 포함되어야 합니다."
    )
    @NotBlank(message = "비밀번호확인은 필수 입력 값입니다.")
    private String confirmPassword;

    @Email(message = "이메일 형식에 맞춰주세요.")
    @NotNull(message = "이메일은 필수 입력 값입니다.")
    private String email;

    @Size(max = 12, message = "닉네임은 12자 이하로 입력해주세요.")
    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    private String nickname;

    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
                .userName(userName)
                .email(email)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .build();
    }

}
