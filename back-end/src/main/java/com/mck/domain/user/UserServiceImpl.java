package com.mck.domain.user;

import com.mck.global.error.BusinessException;
import com.mck.global.error.ErrorCode;
import com.mck.web.dto.UserSignupDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public User signup(UserSignupDto userSignupDto) {
        validateUserSignupDto(userSignupDto);
        User user = userSignupDto.toEntity(bCryptPasswordEncoder);
        return userRepository.save(user);
    }

    public void validateUserSignupDto(UserSignupDto userSignupDto) {
        Optional<User> findUser = userRepository.findByEmail(userSignupDto.getEmail());

        if (findUser == null) {
            throw new BusinessException(ErrorCode.ALREADY_REGISTERED_MEMBER);
        }

        // todo : goldenplanet 메일이 아니면 가입이 안 되게하기.
    }

    @Override
    @Transactional
    public User login(UserSignupDto userRequestDto) {
        return null;
    }
}
