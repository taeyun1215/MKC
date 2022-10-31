package com.mck.domain.user;

import com.mck.domain.base.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.validation.constraints.Email;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@DynamicUpdate // 특정 컬럼만 업데이트 해주기 위함.
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", unique = true, nullable = false)
    private long userId;

    @Email
    @NotBlank(message = "이메일 형식을 맞춰주세요.")
    private String email;

    @Column(length = 100, nullable = false)
    @Pattern(
            regexp = "/^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,20}$/",
            message = "비밀번호는 최소 8자 이상, 하나 이상의 영문, 숫자, 특수문자가 포함되어야 합니다."
    )
    private String password;

    @Column(length = 40, nullable = false)
    @Pattern(
            regexp = "/^\\d{3}-\\d{3,4}-\\d{4}$/",
            message = "핸드폰 번호 형식을 맞춰주세요."
    )
    private String phone;

}
