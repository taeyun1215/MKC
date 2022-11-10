package com.mck.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor // 테스트 코드 작성용
public class UserEditDto {

    @Size(min = 5, max = 15, message = "아이디는 최소 5자 이상 15자 이하입니다.")
    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String userName; // 아이디(수정 불가)

    @Size(min = 8, max = 16)
    @Pattern(
            regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",
            message = "비밀번호는 최소 8자 이상 16자 이하, 하나 이상의 영문, 숫자, 특수문자가 포함되어야 합니다."
    )
    @NotBlank(message = "현재 비밀번호는 필수 입력 값입니다.")
    private String oldPassword; // 현재 비밀번호

    @Size(min = 8, max = 16)
    @Pattern(
            regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",
            message = "비밀번호는 최소 8자 이상 16자 이하, 하나 이상의 영문, 숫자, 특수문자가 포함되어야 합니다."
    )
    private String newPassword; // 새 비밀번호

    @Size(min = 8, max = 16)
    @Pattern(
            regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",
            message = "비밀번호는 최소 8자 이상 16자 이하, 하나 이상의 영문, 숫자, 특수문자가 포함되어야 합니다."
    )
    private String newConfirmPassword; // 새 비밀번호 확인

    @Size(max = 12, message = "닉네임은 12자 이하로 입력해주세요.")
    @NotBlank(message = "현재 닉네임은 필수 입력 값입니다.")
    private String oldNickname; // 현재 닉네임

    @Size(max = 12, message = "닉네임은 12자 이하로 입력해주세요.")
    private String newNickname; // 새 닉네임

}
