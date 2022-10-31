package com.mck.domain.user;

import com.mck.web.dto.UserSignupDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public interface UserService {
    // DB에 유저 저장.
    User signup(UserSignupDto userRequestDto);

    // DB에 유저 저장.
    User login(UserSignupDto userRequestDto);
}
