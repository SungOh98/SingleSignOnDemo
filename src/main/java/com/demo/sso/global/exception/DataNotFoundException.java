package com.demo.sso.global.exception;

public abstract class DataNotFoundException extends RuntimeException{
    protected DataNotFoundException(String message) {
        super(message);
    }
}
