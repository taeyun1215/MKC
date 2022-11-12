package com.mck.domain.post;

import com.mck.domain.post.dto.PostDto;
import com.mck.domain.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public interface PostService {
    // DB에 게시글 저장.
    Post registerPost(PostDto postDto, User user);

    // 게시글 수정.
    Post editPost(Long postId, PostDto postDto, User user);
}
