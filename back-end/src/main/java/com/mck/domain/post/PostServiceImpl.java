package com.mck.domain.post;

import com.mck.domain.user.User;
import com.mck.domain.user.UserRepository;
import com.mck.global.error.ErrorCode;
import com.mck.web.dto.PostDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Post registerPost(PostDto postDto, String userId) {

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_EXISTING_ACCOUNT.getMessage()));

        Post post = postDto.toEntity(user);

        return postRepository.save(post);
    }
}
