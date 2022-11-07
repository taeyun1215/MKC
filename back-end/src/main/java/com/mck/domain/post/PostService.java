package com.mck.domain.post;

import com.mck.domain.user.User;
import com.mck.web.dto.PostDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public interface PostService {
    // DB에 글 저장.
    Post registerPost(PostDto postDto, User user);
}
