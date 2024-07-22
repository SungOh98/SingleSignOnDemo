package com.demo.sso.domain.user.exception;

import com.demo.sso.global.exception.DataNotFoundException;

public class AccountNotFoundException extends DataNotFoundException {
    private static final String DEFAULT_MESSAGE = "아이디를 잘못 입력하셨습니다.";

    private AccountNotFoundException(String message) {
        super(message);
    }


    public static AccountNotFoundException withDetail(String detail) {
        return new AccountNotFoundException(DEFAULT_MESSAGE + "\n세부정보: " + detail);
    }
}
