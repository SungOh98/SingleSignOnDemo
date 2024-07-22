package com.demo.sso.domain.user.exception;

import com.demo.sso.global.exception.DuplicateException;

public class DuplicateAccountException extends DuplicateException {
    private static final String DEFAULT_MESSAGE = "이미 존재하는 계정이 있습니다.";
    protected DuplicateAccountException(String message) {
        super(message);
    }
    public static DuplicateAccountException withDetail(String detail) {
        return new DuplicateAccountException(DEFAULT_MESSAGE + "중복 계정 : " + detail);
    }
}
