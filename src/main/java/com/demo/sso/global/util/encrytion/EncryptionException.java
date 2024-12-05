package com.demo.sso.global.util.encrytion;

public class EncryptionException extends IllegalStateException {
    private static final String DEFAULT_MESSAGE = "데이터 암/복호화 과정에서 문제가 발생했습니다.";

    private EncryptionException(String message) {
        super(message);
    }


    public static EncryptionException withDetail(String detail) {
        return new EncryptionException(DEFAULT_MESSAGE + "\n세부정보: " + detail);
    }
}
