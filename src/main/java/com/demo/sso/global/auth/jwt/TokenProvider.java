package com.demo.sso.global.auth.jwt;

public interface TokenProvider {
    String createToken(String subject);
    String getSubject(String token);
    void validateToken(String token);
}
