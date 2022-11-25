package com.mck.domain.comment;

import com.mck.domain.post.Post;
import com.mck.domain.post.PostRepo;
import com.mck.domain.user.User;
import com.mck.domain.user.UserRepo;
import com.mck.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepo commentRepo;
    private final PostRepo postRepo;
    private final UserRepo userRepo;

    @Override
    @Transactional
    public Comment saveComment(Long postId, User user, CommentDto commentDto) {

        User findUser = userRepo.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_EXISTING_ACCOUNT.getMessage()));

        Optional<Post> findPost = postRepo.findById(postId);

        Comment comment = commentDto.toEntity(findUser, findPost.get());
        Comment saveComment = commentRepo.save(comment);

        log.info("새로운 댓글 정보를 DB에 저장했습니다 : ", saveComment.getId());

        return saveComment;
    }

}
