package com.mck.domain.user;

import com.mck.domain.base.BaseEntity;
import com.mck.domain.comment.Comment;
import com.mck.domain.post.Post;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 외부에서의 생성을 열어 둘 필요가 없을 때 보안상 권장함.
@Getter
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

    @OneToMany
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany
    private List<Post> postList = new ArrayList<>();

}
