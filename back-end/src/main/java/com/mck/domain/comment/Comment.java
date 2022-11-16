package com.mck.domain.comment;

import com.mck.domain.base.BaseEntity;
import com.mck.domain.post.Post;
import com.mck.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseEntity {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "comment_id")
//    private long commentId;
//
//    @Column(
//            length = 500,
//            nullable = false
//    )
//    private String comment; // 코멘트
//
//    @Column(
//            length = 100,
//            nullable = false
//    )
//    private String writer; // 작성
//
//    @ManyToOne(
//            targetEntity = User.class,
//            fetch = FetchType.LAZY
//    ) // 실제로 요청하는 순간 가져오기 위해 LAZY로 사용함.
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @ManyToOne(
//            targetEntity = Post.class,
//            fetch = FetchType.LAZY
//    )
//    @JoinColumn(name = "post_id")
//    private Post post;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column(
            length = 500,
            nullable = false
    )
    private String content; // 내용

    @Column(
            length = 100,
            nullable = false
    )
    private String writer; // 작성자

    @ManyToOne(
            targetEntity = User.class,
            fetch = FetchType.LAZY
    ) // 실제로 요청하는 순간 가져오기 위해 LAZY로 사용함.
    @JoinColumn
    private User user;

    @ManyToOne(
            targetEntity = Post.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn
    private Post post;
}
