package com.demo.sso.domain.user.exception;

import com.demo.sso.global.exception.DataNotFoundException;

public class KakaoAccountNotFoundException extends DataNotFoundException {
    private static final String DEFAULT_MESSAGE = "해당 카카오 계정으로 가입한 사용자가 없습니다. ";

    private KakaoAccountNotFoundException(String message) {
        super(message);
    }


    public static KakaoAccountNotFoundException withDetail(String detail) {
        return new KakaoAccountNotFoundException(DEFAULT_MESSAGE + "\n세부정보: " + detail);
    }
}
