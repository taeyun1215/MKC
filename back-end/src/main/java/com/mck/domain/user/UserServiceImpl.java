package com.mck.domain.user;

import com.mck.global.error.BusinessException;
import com.mck.global.error.ErrorCode;
import com.mck.web.dto.UserSignupDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User signup(UserSignupDto userSignupDto) {
        User user = userSignupDto.toEntity(passwordEncoder);
        validateUserSignupDto(user);
        return userRepository.save(user);
    }

    public void validateUserSignupDto(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new BusinessException(ErrorCode.ALREADY_REGISTERED_MEMBER);
        }
    }

    @Override
    @Transactional
    public User login(UserSignupDto userRequestDto) {
        return null;
    }
}
