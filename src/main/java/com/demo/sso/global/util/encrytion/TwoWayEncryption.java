package com.demo.sso.global.util.encrytion;

public interface TwoWayEncryption {
    String encrypt(String plainText);
    String decrypt(String cipherText);
}
