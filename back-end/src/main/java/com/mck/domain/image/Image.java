package com.mck.domain.image;

import com.mck.domain.base.BaseEntity;
import com.mck.domain.post.Post;
import com.mck.domain.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 외부에서의 생성을 열어 둘 필요가 없을 때 보안상 권장함.
@Table(name = "image")
@Getter
public class Image extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private long imageId;

    @Column(
            length = 200,
            nullable = false
    )
    private String originalImageName; // 원본 이미지 파일

    @Column(
            length = 500,
            nullable = false
    )
    private String imageName; // 실제로 로컬에 저장할 이미지 파일명

    @Column(
            length = 500,
            nullable = false
    )
    private String imageUrl; // 이미지 조회 경로

    @ManyToOne(
            targetEntity = Post.class,
            fetch = FetchType.LAZY
    ) // 실제로 요청하는 순간 가져오기 위해 LAZY로 사용함.
    @JoinColumn(name = "post_id")
    private Post post;

}
