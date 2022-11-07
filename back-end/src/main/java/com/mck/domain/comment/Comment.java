package com.mck.domain.comment;

import com.mck.domain.base.BaseEntity;
import com.mck.domain.post.Post;
import com.mck.domain.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 외부에서의 생성을 열어 둘 필요가 없을 때 보안상 권장함.
@Table(name = "comment")
@Getter
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private long commentId;

    @Column(
            length = 500,
            nullable = false
    )
    private String comment; // 코멘트

    @Column(
            length = 100,
            nullable = false
    )
    private String writer; // 작성

    @ManyToOne(
            targetEntity = User.class,
            fetch = FetchType.LAZY
    ) // 실제로 요청하는 순간 가져오기 위해 LAZY로 사용함.
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(
            targetEntity = Post.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "post_id")
    private Post post;
}
