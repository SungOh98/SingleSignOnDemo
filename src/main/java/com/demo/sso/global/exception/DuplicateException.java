package com.demo.sso.global.exception;

public abstract class DuplicateException extends RuntimeException {
    protected DuplicateException(String message) {
        super(message);
    }

}
