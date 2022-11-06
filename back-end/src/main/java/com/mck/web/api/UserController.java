package com.mck.web.api;

import com.mck.domain.user.User;
import com.mck.domain.user.UserService;
import com.mck.global.error.BusinessException;
import com.mck.global.error.ErrorCode;
import com.mck.global.service.UserDetailsService;
import com.mck.web.dto.UserEditDto;
import com.mck.web.dto.UserSignupDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 유저 등록
    @PostMapping("/signup")
    public ResponseEntity<User> signup (
            @Validated @ModelAttribute("userSignupDto") UserSignupDto userSignupDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) { // 유효성 검사
            bindingResult.reject("mismatchedFormat", ErrorCode.MISMATCHED_FORMAT.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (!StringUtils.equals( // 비밀번호, 비밀번호 확인 일치 유무
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
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<User> edit (
            @Validated @ModelAttribute("userEditDto") UserEditDto userEditDto,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDetailsService userDetailsService
    ) {
        User loginUser = userDetailsService.getUser();

        if (bindingResult.hasErrors()) { // 유효성 검사
            bindingResult.reject("mismatchedFormat", ErrorCode.MISMATCHED_FORMAT.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (!StringUtils.equals( // 새 비밀번호, 새 비밀번호 확인 일치 유무
                userEditDto.getNewPassword(),
                userEditDto.getNewConfirmPassword()
        )) {
            bindingResult.reject("mismatchedPassword", ErrorCode.MISMATCHED_PASSWORD.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (!bCryptPasswordEncoder.matches( // 로그인 한 유저의 비밀번호, 입력한 비밀번호 일치 유무
                loginUser.getPassword(),
                userEditDto.getNewPassword()
        )) {
            bindingResult.reject("mismatchedExistPasswordAndEnterPassword", ErrorCode.MISMATCHED_EXIST_PASSWORD_AND_ENTER_PASSWORD.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (!StringUtils.equals( // 현재 닉네임과 입력한 닉네임 일치 유무
                loginUser.getNickname(),
                userEditDto.getNewNickname()
        )) {
            bindingResult.reject("mismatchedExistNicknameAndEnterNickname", ErrorCode.MISMATCHED_EXIST_NICKNAME_AND_ENTER_NICKNAME.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            userService.editUser(userEditDto, loginUser);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

}
