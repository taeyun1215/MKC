package com.mck.domain.image;

import com.mck.domain.post.Post;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "image")
@Getter
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

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
    private Post post;

    public void updateItemImage(String originalImageName, String imageName, String imageUrl){
        this.originalImageName = originalImageName;
        this.imageName = imageName;
        this.imageUrl = imageUrl;
    }

    public void initImageInfo() {
        this.originalImageName = "";
        this.imageName = "";
        this.imageUrl = "";
    }

}
