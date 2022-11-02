package com.mck.web.api;

import com.mck.domain.user.User;
import com.mck.domain.user.UserService;
import com.mck.web.dto.UserSignupDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.util.StringUtils;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> signup(
            @Validated @RequestBody UserSignupDto userSignupDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return resp
        } else if (!StringUtils.equals(
                userSignupDto.getPassword(),
                userSignupDto.getConfirmPassword()
        )) {
            bindingResult.reject("mismatchedPassword");
            return
        }

        try {
            userService.signup(userSignupDto);
        } catch (BusinessException e) {
            bindingResult.reject("alreadyMember");
            return
        }
    }

}
