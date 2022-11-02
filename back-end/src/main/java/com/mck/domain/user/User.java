package com.mck.domain.user;

import com.mck.domain.base.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

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
    @Column(name = "user_id")
    private long userId;

    @Column(
            length = 50,
            nullable = false,
            unique = true
    )
    private String email;

    @Column(
            length = 200,
            nullable = false
    )
    private String password;

    @Column(
            length = 100,
            nullable = false
    )
    private String name;

    @Column(
            length = 40,
            nullable = false
    )
    private String phone;

}
