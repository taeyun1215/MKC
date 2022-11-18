package com.mck.domain.user;

import com.mck.domain.base.BaseEntity;
import com.mck.domain.role.Role;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.GenerationType.AUTO;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class User {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO) // IDENTITY에서 바꾼 이유 : DB에서 삭제해도 계속해서 PK값이 증가함.
//    @Column(name = "user_id")
//    private Long userId; // 고유값
//
//    @Column(
//            length = 50,
//            nullable = false,
//            unique = true,
//            name = "user_name"
//    )
//    private String userName; // 아이디, 중복체크
//
//    @Column(
//            length = 200,
//            nullable = false
//    )
//    private String password; // 비밀번호
//
//    @Column(
//            length = 50,
//            nullable = false,
//            unique = true
//    )
//    private String email; // 이메일, 중복체크
//
//    @Column(
//            length = 100,
//            nullable = false,
//            unique = true
//    )
//    private String nickname; // 닉네임, 중복체크

    @Id @GeneratedValue(strategy = AUTO)
    private Long id;
    private String nickname; // 닉네임
    private String username;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();
    private String email;
    private boolean emailVerified; // 이메일 인증 여부
    private LocalDateTime joinedAt; // 로그인한 시간

    // 회원가입 완료 처리
    public void completeSignUp() {
        this.emailVerified = true;
        this.joinedAt = LocalDateTime.now();
    }

}
