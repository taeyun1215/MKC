package com.mck.domain.user;

import com.mck.domain.base.BaseEntity;
import com.mck.domain.role.Role;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            length = 100,
            nullable = false,
            unique = true
    )
    private String nickname; // 닉네임

    @Column(
            length = 50,
            nullable = false,
            unique = true
    )
    private String username; // 아이디

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
    private String email; // 이메일

    private boolean emailVerified; // 이메일 인증 여부

    private LocalDateTime joinedAt; // 로그인한 시간

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = {@JoinColumn(name = "id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "name", referencedColumnName = "name")}
    )
    private Collection<Role> roles = new ArrayList<>();  // todo : 권한은 하나만 주는 걸로 해도 되지 않은가?

    // 회원가입 완료 처리
    public void completeSignUp() {
        this.emailVerified = true;
        this.joinedAt = LocalDateTime.now();
    }

}
