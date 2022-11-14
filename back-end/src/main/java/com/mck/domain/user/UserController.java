package com.mck.domain.user;

import com.mck.global.error.BusinessException;
import com.mck.global.error.ErrorCode;
import com.mck.global.service.UserDetailsService;
import com.mck.domain.user.dto.UserEditDto;
import com.mck.domain.user.dto.UserSignupDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
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
        } else if (!StringUtils.equals( userSignupDto.getPassword(), userSignupDto.getConfirmPassword())) { // 비밀번호, 비밀번호 확인 일치 유무
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

    // 유저 수정
    @PutMapping("/edit")
    public ResponseEntity<User> edit (
            @Validated @ModelAttribute("userEditDto") UserEditDto userEditDto,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDetailsService userDetailsService
    ) {
        User loginUser = userDetailsService.getUser();

        if (bindingResult.hasErrors()) { // 유효성 검사
            bindingResult.reject("mismatchedFormat", ErrorCode.MISMATCHED_FORMAT.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (!StringUtils.equals(userEditDto.getNewPassword(), userEditDto.getNewConfirmPassword())) { // 새 비밀번호, 새 비밀번호 확인 일치 유무
            bindingResult.reject("mismatchedPassword", ErrorCode.MISMATCHED_PASSWORD.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (!bCryptPasswordEncoder.matches(loginUser.getPassword(), userEditDto.getNewPassword())) { // 로그인 한 유저의 비밀번호, 입력한 비밀번호 일치 유무
            bindingResult.reject("mismatchedEnterPassword", ErrorCode.MISMATCHED_ENTER_PASSWORD.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (!StringUtils.equals(loginUser.getNickname(), userEditDto.getNewNickname())) { // 현재 닉네임과 입력한 닉네임 일치 유무
            bindingResult.reject("mismatchedEnterNickname", ErrorCode.MISMATCHED_ENTER_NICKNAME.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            userService.editUser(userEditDto, loginUser);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    // 유저 삭제
    @DeleteMapping("/delete")
    public ResponseEntity<User> delete(
            String password, BindingResult bindingResult,
            @AuthenticationPrincipal UserDetailsService userDetailsService
    ) {
        User loginUser = userDetailsService.getUser();

        if (!bCryptPasswordEncoder.matches(password, loginUser.getPassword())) { // 로그인 한 유저의 비밀번호, 입력한 비밀번호 일치 유무
            bindingResult.reject("mismatchedEnterPassword", ErrorCode.MISMATCHED_ENTER_PASSWORD.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            userService.deleteUser(loginUser);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
