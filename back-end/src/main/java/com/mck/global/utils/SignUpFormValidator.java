package com.mck.global.utils;

import com.mck.domain.user.UserRepo;
import com.mck.web.dto.SignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class SignUpFormValidator implements Validator {

    private final UserRepo userRepo;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(SignUpForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SignUpForm signUpForm = (SignUpForm) target;
        if(userRepo.existsByUsername(signUpForm.getUsername())){
            errors.rejectValue("username", "invalid.username", new Object[]{signUpForm.getUsername()}, "이미 사용중인 아이디 입니다.");
        }
    }
}
