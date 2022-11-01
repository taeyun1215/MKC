package com.mck.domain.user;

import com.mck.web.dto.UserSignupDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User signup(UserSignupDto userRequestDto) {
        return null;
    }

    @Override
    public User login(UserSignupDto userRequestDto) {
        return null;
    }
}
