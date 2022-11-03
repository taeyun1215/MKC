package com.mck.domain.post;

import com.mck.domain.base.BaseEntity;
import com.mck.domain.comment.Comment;
import com.mck.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 외부에서의 생성을 열어 둘 필요가 없을 때 보안상 권장함.
@Table(name = "post")
@Getter
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private long postId;

    @Column(
            length = 50,
            nullable = false
    )
    private String title;

    @Column(
            length = 500,
            nullable = false
    )
    private String content;

    @ManyToOne(
            targetEntity = User.class,
            fetch = FetchType.LAZY
    ) // 실제로 요청하는 순간 가져오기 위해 LAZY로 사용함.
    @JoinColumn(name = "user_name")
    private String writer;

    @OneToMany
    private List<Comment> commentList;
}
