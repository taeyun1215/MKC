package com.mck.domain.user;

import com.mck.global.error.BusinessException;
import com.mck.global.error.ErrorCode;
import com.mck.domain.user.dto.UserEditDto;
import com.mck.domain.user.dto.UserLoginDto;
import com.mck.domain.user.dto.UserSignupDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
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
        Optional<User> findUserEmail = userRepository.findByEmail(userSignupDto.getEmail());
        Optional<User> findUserNickname = userRepository.findByNickname(userSignupDto.getUserName());

        if (findUserEmail.isPresent()) {
            throw new BusinessException(ErrorCode.ALREADY_REGISTERED_MEMBER);
        }
        if (findUserNickname.isPresent()) {
            throw new BusinessException(ErrorCode.ALREADY_EXIST_NICKNAME);
        }

        // todo : goldenplanet 메일이 아니면 가입이 안 되게하기.
    }

    @Override
    @Transactional
    public User login(UserLoginDto userLoginDto) {
        return null;
    }

    @Override
    @Transactional
    public User editUser(UserEditDto userEditDto, User user) {
        validateUserEditDto(userEditDto);
        userRepository.editUser(userEditDto.getNewNickname(), userEditDto.getNewPassword(), user.getUserId());

        return user;
    }

    public void validateUserEditDto(UserEditDto userEditDto) {
        Optional<User> findUserNickname = userRepository.findByNickname(userEditDto.getNewNickname());

        if (findUserNickname.isPresent()) {
            throw new BusinessException(ErrorCode.ALREADY_EXIST_NICKNAME);
        }
    }

    @Override
    @Transactional
    public User deleteUser(User user) {
        userRepository.delete(user);
        return user;
    }

}
