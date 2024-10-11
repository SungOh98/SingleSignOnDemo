package com.demo.sso.global.auth.exception;

public class UserTokenExpiredException extends RuntimeException {
    public UserTokenExpiredException(String message) {
        super(message);
    }
}
