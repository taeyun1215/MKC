package com.mck.domain.post;

import com.mck.domain.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public interface PostService {
    // 게시글 전체 반환.
    List<Post> getPostAll();

    // DB에 게시글 저장.
    Post registerPost(PostDto postDto, User user);

    // 게시글 수정.
    Post editPost(Long postId, PostDto postDto, User user);

    // 게시글 삭제
    Post deletePost(Long postId, User user);
}