package com.mck.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor // 테스트 코드 작성용
public class UserLoginDto {

    @NotBlank(message = "아이디은 필수 입력 값입니다.")
    private String id;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;

}
