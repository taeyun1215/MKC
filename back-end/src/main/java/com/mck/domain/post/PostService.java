package com.mck.domain.post;

import com.mck.domain.post.request.PostDto;
import com.mck.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@Transactional(readOnly = true)
public interface PostService {

    // 게시글 Paging
    Page<Post> pagePostList(Pageable pageable);

    // 게시글 전체 반환
    List<Post> getPostAll();

    // 게시글 검색
    Page<Post> searchPost(String keyword, Pageable pageable);

    // 게시글 디테일
    Post viewDetailPost(Long postId);

    // 게시글 저장
    Post savePost(PostDto postDto, User user) throws IOException;

    // 게시글 수정
    void editPost(Long postId, PostDto postDto, User user) throws IOException;

    // 게시글 삭제
    void deletePost(Long postId, User user) throws IOException;

    // 게시글 좋아요
    String likePost(Long postId, User user);

    // 게시글 조회수
    void updateViewPost(Long postId);

    // 인기 게시글
    List<Post> popularPost();

    // 내가 쓴 게시글
    List<Post> myPost(String username);

}