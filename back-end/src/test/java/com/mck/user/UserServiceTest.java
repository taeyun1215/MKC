package com.mck.user;

import com.mck.domain.user.User;
import com.mck.domain.user.UserRepository;
import com.mck.domain.user.UserService;
import com.mck.web.dto.UserEditDto;
import com.mck.web.dto.UserSignupDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Transactional  // 테스트는 여러번 반복해서 실행해야 하므로 DB에 반영이 안 되게 하기 위해서 사용함.
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("회원가입 테스트")
    void signup() {
        // given
        UserSignupDto user = new UserSignupDto(
                    "devty1215",
                    "qwer123!@#",
                    "qwer123!@#",
                    "taeyun1215@naver.com",
                    "gp_dted"
                );

        // when
        User saveUser = userService.signup(user);

        // then
        Optional<User> findUser = userRepository.findByEmail("taeyun1215@naver.com");
        Assertions.assertEquals(saveUser, findUser.get());
    }

    @Test
    @DisplayName("로그인 테스트")
    void login() {
        // given

        // when

        // then
    }

    @Test
    @DisplayName("유저 정보 수정 테스트")
    void editUser() {
        // given
        UserSignupDto userSignupDto = new UserSignupDto(
                "devty1215",
                "qwer123!@#",
                "qwer123!@#",
                "taeyun1215@naver.com",
                "gp_dted"
        );

        User saveUser = userService.signup(userSignupDto);

        UserEditDto userEditDto = new UserEditDto(
                "devty1215",
                "qwer123!@#",
                "222qqqq@@@@",
                "222qqqq@@@@",
                "gp_dted",
                "tytytyty"
        );

        // when
        User editUserId = userService.editUser(userEditDto, saveUser);

        // then
        Optional<User> editUser = userRepository.findByUserId(editUserId.getUserId());
        Optional<User> findUser = userRepository.findByUserName("devty1215");

        Assertions.assertEquals(editUser.get().getNickname(), findUser.get().getNickname());
        Assertions.assertEquals(editUser.get().getPassword(), findUser.get().getPassword());
    }

    @Test
    @DisplayName("유저 정보 삭제 테스트")
    void deleteUser() {
        // given
        UserSignupDto userSignupDto = new UserSignupDto(
                "devty1215",
                "qwer123!@#",
                "qwer123!@#",
                "taeyun1215@naver.com",
                "gp_dted"
        );

        User saveUser = userService.signup(userSignupDto);

        // when
        userService.deleteUser(saveUser);

        // then
        Optional<User> deleteUser = userRepository.findByUserId(saveUser.getUserId());
        Assertions.assertEquals(deleteUser.isPresent(), false);
    }

}
