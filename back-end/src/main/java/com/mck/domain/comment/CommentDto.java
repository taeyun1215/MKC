package com.mck.domain.comment;

import com.mck.domain.post.Post;
import com.mck.domain.user.User;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor // 테스트 코드 작성용
public class CommentDto {

    @NotBlank(message = "댓글은 필수 입력 값입니다.")
    private String content; // 내용

    public Comment toEntity(User user, Post post) {
        return Comment.builder()
                .content(content)
                .writer(user.getNickname())
                .user(user)
                .post(post)
                .build();
    }

}
