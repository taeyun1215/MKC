package com.mck.domain.image;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor // 테스트 코드 작성용
public class ImageDto {
    private Long id;
    private String originalImageName;
    private String imageName;
    private String imageUrl;

    public Image toEntity() {
        return Image.builder()
                .id(id)
                .originalImageName(originalImageName)
                .imageName(imageName)
                .imageUrl(imageUrl)
                .build();
    }

}