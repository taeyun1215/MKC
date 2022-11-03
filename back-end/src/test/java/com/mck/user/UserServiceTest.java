package com.mck.user;

import com.mck.domain.user.User;
import com.mck.domain.user.UserRepository;
import com.mck.domain.user.UserService;
import com.mck.web.dto.UserSignupDto;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
@Transactional  // 테스트는 여러번 반복해서 실행해야 하므로 DB에 반영이 안 되게 하기 위해서 사용함.
@RequiredArgsConstructor
public class UserServiceTest {

    private final UserService userService;
    private final UserRepository userRepository;

    @Test
    @DisplayName("회원가입 테스트")
    void signup() {
        // given
        UserSignupDto user = new UserSignupDto(
                    "devty1215",
                    "woogi101",
                    "woogi101",
                    "taeyun1215@naver.com",
                    "gp_dted"
                );

        // when
        userService.signup(user);

        // then
        User findUser = userRepository.findByEmail("taeyun1215@naver.com");
        assertThat(findUser).isEuqualTo(user);
    }

    @Test
    void login() {

    }
}
