package com.demo.sso.global.util.encrytion;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class AESGCMEncryption implements TwoWayEncryption {

    private final int IV_SIZE = 12;   // Nonce 크기 (12바이트)
    private final int TAG_SIZE = 128; // 인증 태그 크기 (128비트)

    @Value("${encryption.key}")
    private String key; // Base64로 인코딩된 키

    // AES-GCM 암호화
    public String encrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            byte[] iv = generateIV(); // 매번 새 Nonce 생성
            GCMParameterSpec spec = new GCMParameterSpec(TAG_SIZE, iv);
            cipher.init(Cipher.ENCRYPT_MODE, getKey(), spec);

            byte[] encrypted = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            byte[] encryptedWithIv = new byte[iv.length + encrypted.length];
            System.arraycopy(iv, 0, encryptedWithIv, 0, iv.length);
            System.arraycopy(encrypted, 0, encryptedWithIv, iv.length, encrypted.length);

            return Base64.getEncoder().encodeToString(encryptedWithIv); // Base64로 인코딩
        } catch (Exception e) {
            throw EncryptionException.withDetail(e.getMessage());
        }
    }

    // AES-GCM 복호화
    public String decrypt(String encryptedData) {
        try {
            byte[] decoded = Base64.getDecoder().decode(encryptedData);
            byte[] iv = new byte[IV_SIZE];
            byte[] encryptedBytes = new byte[decoded.length - IV_SIZE];

            System.arraycopy(decoded, 0, iv, 0, IV_SIZE);
            System.arraycopy(decoded, IV_SIZE, encryptedBytes, 0, encryptedBytes.length);

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec spec = new GCMParameterSpec(TAG_SIZE, iv);
            cipher.init(Cipher.DECRYPT_MODE, getKey(), spec);

            byte[] decrypted = cipher.doFinal(encryptedBytes);
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw EncryptionException.withDetail(e.getMessage());
        }
    }

    // IV 생성
    private byte[] generateIV() {
        byte[] iv = new byte[IV_SIZE];
        new SecureRandom().nextBytes(iv);
        return iv;
    }

    private SecretKeySpec getKey() {
        // Base64 문자열 -> Byte 배열로 디코딩
        if (key == null) throw EncryptionException.withDetail("암/복호화 키가 없습니다.");
        byte[] keyBytes;

        try {
            keyBytes = Base64.getDecoder().decode(key);
        } catch (IllegalArgumentException e) {
            throw EncryptionException.withDetail("암/복호화 키가 Base64 인코딩 형태가 아닙니다.");
        }

        if (keyBytes.length != 32) throw EncryptionException.withDetail("암/복호화 키가 올바르지 않습니다. 256비트 키가 필요합니다.");

        return new SecretKeySpec(keyBytes, "AES");
    }
}
