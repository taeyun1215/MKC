package com.mck.domain.post;

import com.mck.domain.user.User;
import com.mck.domain.user.UserRepo;
import com.mck.global.error.BusinessException;
import com.mck.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final PostRepo postRepo;
    private final UserRepo userRepo;

    @Override
    @Transactional
    public List<Post> getPostAll() {
        log.info("모든 게시글을 가져옵니다.");
        return postRepo.findAll();
    }

    @Override
    @Transactional
    public Post registerPost(PostDto postDto, User user) {
        User findUser = userRepo.findById(user.getId()) // 스프링으로 로그인한 회원을 가져오지만 한번 더 DB에 있는지 조회함.
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_EXISTING_ACCOUNT.getMessage()));

        Post post = postDto.toEntity(findUser);
        log.info("새로운 게시글 정보를 DB에 저장했습니다 : ", post.getTitle());

        return postRepo.save(post);
    }

    @Override
    @Transactional
    public Post editPost(Long postId, PostDto postDto, User user) {
        User findUser = userRepo.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_EXISTING_ACCOUNT.getMessage()));

        validatePostEdit(postId, findUser);
        postRepo.editPost(postDto.getTitle(), postDto.getContent(), postId);
        // log.info("게시글 정보 수정했습니다. ", post.getTitle());

        return postDto.toEntity(user);
    }

    public void validatePostEdit(Long postId, User findUser) {
        Optional<Post> findPostId = postRepo.findById(postId);
        Optional<Post> findPostIdAndUserId = postRepo.findByIdAndUser(postId, findUser);

        if (findPostId.isEmpty()) {
            throw new BusinessException(ErrorCode.NOT_EXIST_POST);
        } else if (findPostIdAndUserId.isEmpty()) {
            throw new BusinessException(ErrorCode.NOT_EDIT_PERMISSION_POST);
        }

    }

    @Override
    @Transactional
    public Post deletePost(Long postId, User user) {
        User findUser = userRepo.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_DELETE_PERMISSION_POST.getMessage()));

        Optional<Post> findPost = validatePostDelete(postId, findUser);
        postRepo.delete(findPost.get());

        return findPost.get();
    }

    public Optional<Post> validatePostDelete(Long postId, User findUser) {
        Optional<Post> findPostId = postRepo.findById(postId);
        Optional<Post> findPostIdAndUserId = postRepo.findByIdAndUser(postId, findUser);

        if (findPostId.isEmpty()) {
            throw new BusinessException(ErrorCode.NOT_EXIST_POST);
        } else if (findPostIdAndUserId.isEmpty()) {
            throw new BusinessException(ErrorCode.NOT_DELETE_PERMISSION_POST);
        }

        return findPostIdAndUserId;
    }
}
