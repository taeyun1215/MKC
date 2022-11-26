package com.mck.domain.comment;

import com.mck.domain.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public interface CommentService {

    Comment saveComment(Long postId, User user, CommentDto commentDto);

    Comment saveReComment(Long postId, Long commentId, User user, CommentDto commentDto);
}
