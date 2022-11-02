package com.mck.user;

import com.mck.domain.user.User;
import com.mck.domain.user.UserService;
import com.mck.domain.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

@RequiredArgsConstructor
public class UserServiceTest {

    private final UserService userService

    @Test
    void signup() {
        // given
        User user = new User(1L, "taeyun125@naver.com", "woogi101^^", "010-2415-6806");

        // when
        userService.signup(user);
        User findUser = userService.

        // then

    }

    @Test
    void login() {

    }
}
