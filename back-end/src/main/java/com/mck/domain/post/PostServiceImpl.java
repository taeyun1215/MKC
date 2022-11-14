package com.mck.domain.post;

import com.mck.domain.post.dto.PostDto;
import com.mck.domain.user.User;
import com.mck.domain.user.UserRepository;
import com.mck.global.error.BusinessException;
import com.mck.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Post registerPost(PostDto postDto, User user) {
        User findUser = userRepository.findByUserId(user.getUserId()) // 스프링으로 로그인한 회원을 가져오지만 한번 더 DB에 있는지 조회함.
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_EXISTING_ACCOUNT.getMessage()));

        Post post = postDto.toEntity(findUser);

        return postRepository.save(post);
    }

    @Override
    @Transactional
    public Post editPost(Long postId, PostDto postDto, User user) {
        User findUser = userRepository.findByUserId(user.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_EXISTING_ACCOUNT.getMessage()));

        Optional<Post> findPost = validatePostEdit(postId, findUser);
        postRepository.editPost(postDto.getTitle(), postDto.getContent(), postId);

        return findPost.get();
    }

    public Optional<Post> validatePostEdit(Long postId, User findUser) {
        Optional<Post> findPostId = postRepository.findByPostId(postId);
        Optional<Post> findPostIdAndUserId = postRepository.findByPostIdAndUser(postId, findUser);

        if (findPostId.isPresent()) {
            throw new BusinessException(ErrorCode.NOT_EXIST_POST);
        } else if (findPostIdAndUserId.isPresent()) {
            throw new BusinessException(ErrorCode.NOT_EDIT_PERMISSION_POST);
        }

        return findPostIdAndUserId;
    }

    @Override
    @Transactional
    public Post deletePost(Long postId, User user) {
        User findUser = userRepository.findByUserId(user.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_DELETE_PERMISSION_POST.getMessage()));

        Optional<Post> findPost = validatePostDelete(postId, findUser);
        postRepository.delete(findPost.get());

        return findPost.get();
    }

    public Optional<Post> validatePostDelete(Long postId, User findUser) {
        Optional<Post> findPostId = postRepository.findByPostId(postId);
        Optional<Post> findPostIdAndUserId = postRepository.findByPostIdAndUser(postId, findUser);

        if (findPostId.isPresent()) {
            throw new BusinessException(ErrorCode.NOT_EXIST_POST);
        } else if (findPostIdAndUserId.isPresent()) {
            throw new BusinessException(ErrorCode.NOT_DELETE_PERMISSION_POST);
        }

        return findPostIdAndUserId;
    }
}
