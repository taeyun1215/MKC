package com.mck.domain.post;

import com.mck.domain.user.User;
import com.mck.domain.user.UserRepo;
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

    private final PostRepo postRepo;
    private final UserRepo userRepo;

    @Override
    @Transactional
    public Post registerPost(PostDto postDto, String userId) {

        User user = userRepo.findByUsername(userId);

        Post post = postDto.toEntity(user);

        return postRepo.save(post);
    }
}
