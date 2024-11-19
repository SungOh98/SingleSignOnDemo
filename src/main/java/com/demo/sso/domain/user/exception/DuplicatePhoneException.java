package com.demo.sso.domain.user.exception;

import com.demo.sso.global.exception.DuplicateException;

public class DuplicatePhoneException extends DuplicateException {
    private static final String DEFAULT_MESSAGE = "이미 존재하는 핸드폰 번호가 있습니다.";
    protected DuplicatePhoneException(String message) {
        super(message);
    }
    public static DuplicatePhoneException withDetail(String detail) {
        return new DuplicatePhoneException(DEFAULT_MESSAGE + "중복 폰번호 : " + detail);
    }
}