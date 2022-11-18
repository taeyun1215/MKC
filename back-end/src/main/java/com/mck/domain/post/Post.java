package com.mck.domain.post;

import com.mck.domain.base.BaseEntity;
import com.mck.domain.comment.Comment;
import com.mck.domain.postlike.PostLike;
import com.mck.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "post_id")
//    private long postId;
//
//    @Column(
//            length = 50,
//            nullable = false
//    )
//    private String title; // 제목
//
//    @Column(
//            length = 500,
//            nullable = false
//    )
//    private String content; // 내용
//
//    @Column(
//            length = 100,
//            nullable = false
//    )
//    private String writer; // 작성자
//
//    @ManyToOne(
//            targetEntity = User.class,
//            fetch = FetchType.LAZY
//    ) // 실제로 요청하는 순간 가져오기 위해 LAZY로 사용함.
//    @JoinColumn(name = "user_id")
//    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column(
            nullable = false
    )
    private String title; // 제목

    @Column(
            nullable = false
    )
    private String content; // 내용

    @Column(
            nullable = false
    )
    private String writer; // 작성자

    @Column(
            nullable = false
    )
    private int view = 0; // 조회수

    @ManyToOne(
            targetEntity = User.class,
            fetch = FetchType.LAZY
    ) // 실제로 요청하는 순간 가져오기 위해 LAZY로 사용함.
    @JoinColumn(name = "username")
    private User user;

    @OneToMany(
            targetEntity = Comment.class,
            mappedBy = "post",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @OrderBy("id DESC")
    private List<Comment> comment; // 댓글

    @OneToMany(
            targetEntity = PostLike.class,
            mappedBy = "post",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<PostLike> likes; // 좋아요
}
