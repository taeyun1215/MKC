package com.mck.domain.post;

import com.mck.domain.base.BaseEntity;
import com.mck.domain.comment.Comment;
import com.mck.domain.image.Image;
import com.mck.domain.postlike.PostLike;
import com.mck.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Post {

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
            targetEntity = Image.class,
            mappedBy = "post",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @OrderBy("id DESC")
    private List<Image> images; // 댓글

    @OneToMany(
            targetEntity = Comment.class,
            mappedBy = "post",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @OrderBy("id DESC")
    private List<Comment> comments; // 댓글

    @OneToMany(
            targetEntity = PostLike.class,
            mappedBy = "post",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<PostLike> likes; // 좋아요

    // Board에서 파일 처리 위함
    public void addPhoto(Image image) {
        this.images.add(image);
    }
}
