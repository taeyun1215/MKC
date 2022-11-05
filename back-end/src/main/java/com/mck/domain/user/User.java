package com.mck.domain.user;

import com.mck.domain.base.BaseEntity;
import lombok.*;
import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 외부에서의 생성을 열어 둘 필요가 없을 때 보안상 권장함.
@Table(name = "users")
@Getter
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // IDENTITY에서 바꾼 이유 : DB에서 삭제해도 계속해서 PK값이 증가함.
    @Column(name = "user_id")
    private Long userId; // 고유값

    @Column(
            length = 50,
            nullable = false,
            unique = true,
            name = "user_name"
    )
    private String userName; // 아이디, 중복체크

    @Column(
            length = 200,
            nullable = false
    )
    private String password; // 비밀번호

    @Column(
            length = 50,
            nullable = false,
            unique = true
    )
    private String email; // 이메일, 중복체크

    @Column(
            length = 100,
            nullable = false,
            unique = true
    )
    private String nickname; // 닉네임, 중복체크

}
