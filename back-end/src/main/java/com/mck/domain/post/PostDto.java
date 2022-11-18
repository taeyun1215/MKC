package com.mck.domain.post;

import com.mck.domain.post.Post;
import com.mck.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor // 테스트 코드 작성용
public class PostDto {

    @NotBlank(message = "제목은 필수 입력 값입니다.")
    private String title;

    @NotBlank(message = "내용은 필수 입력 값입니다.")
    private String content;

    private String writer;

    public Post toEntity(User user) {
        return Post.builder()
                .title(title)
                .content(content)
                .writer(user.getNickname())
                .user(user)
                .build();
    }
}
