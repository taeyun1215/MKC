package com.mck.domain.comment;

import com.mck.domain.post.Post;
import com.mck.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {

    // 댓글에 대한 권한 조회
    Comment findByIdAndUser(Long id, User user);

    // 댓글 정렬
    List<Comment> findByPostOrderByIdAsc(Post post);
}
