package com.mck.domain.user;

import com.mck.web.dto.UserEditDto;
import com.mck.web.dto.UserLoginDto;
import com.mck.web.dto.UserSignupDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public interface UserService {
    // DB에 유저 저장.
    User signup(UserSignupDto userRequestDto);

    // 로그인.
    User login(UserLoginDto userLoginDto);

    // 유저 정보 수정.
    User editUser(UserEditDto userEditDto, User user);

    // 유저 정보 삭제.
    User deleteUser(User user);

}