package com.mck.global.error;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;

public class UserNotFoundExceptionHandler extends UsernameNotFoundException {

    public UserNotFoundExceptionHandler(String msg) {
        super(msg);
    }

    public UserNotFoundExceptionHandler(String msg, Throwable cause) {
        super(msg, cause);
    }
}
