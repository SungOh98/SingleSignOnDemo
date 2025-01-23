package com.demo.sso.domain.user.exception;

import com.demo.sso.global.exception.DataNotFoundException;

public class UserNotFoundException extends DataNotFoundException {
    private static final String DEFAULT_MESSAGE = "해당 회원을 찾을 수 없습니다.";

    private UserNotFoundException(String message) {
        super(message);
    }


    public static UserNotFoundException withDetail(String detail) {
        return new UserNotFoundException(DEFAULT_MESSAGE + "\n세부정보: " + detail);
    }
}
