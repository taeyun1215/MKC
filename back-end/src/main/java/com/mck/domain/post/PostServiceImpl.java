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
@Slf4j
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

        Post post = postDto.toEntity(findUser);
        validatePostEditDto(post);

        postRepository.editPost(post.getTitle(), post.getContent(), postId);

        return post;
    }

    public void validatePostEditDto(Post post) {
        Optional<Post> findPost = postRepository.findByPostId(post.getPostId());

        if (findPost.isPresent()) {
            throw new BusinessException(ErrorCode.NOT_EXIST_POST);
        }
    }
}
