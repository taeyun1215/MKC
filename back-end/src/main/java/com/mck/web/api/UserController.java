package com.mck.web.api;

import com.mck.domain.user.User;
import com.mck.domain.user.UserService;
import com.mck.global.error.BusinessException;
import com.mck.global.error.ErrorCode;
import com.mck.web.dto.UserSignupDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @PostMapping("/signup")
    public ResponseEntity<User> signup(
            @Validated @ModelAttribute("userSignupDto") UserSignupDto userSignupDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            bindingResult.reject("mismatchedFormat", ErrorCode.MISMATCHED_FORMAT.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (!StringUtils.equals(
                userSignupDto.getPassword(),
                userSignupDto.getConfirmPassword()
        )) {
            bindingResult.reject("mismatchedPassword", ErrorCode.MISMATCHED_PASSWORD.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            userService.signup(userSignupDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BusinessException e) {
            bindingResult.reject("alreadyMember", ErrorCode.ALREADY_REGISTERED_MEMBER.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
