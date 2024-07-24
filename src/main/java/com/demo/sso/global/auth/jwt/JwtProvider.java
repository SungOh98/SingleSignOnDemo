package com.demo.sso.global.auth.jwt;

import com.demo.sso.global.auth.exception.UnAuthorizationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

/**
 * Jwt 토큰을 생성하고 검증하는 클래스
 */
@Component
@Slf4j
public class JwtProvider implements TokenProvider{
    private final String secretKey;
    private final long EXPIRE_TIME = 1000 * 60 * 30;

    public JwtProvider(@Value("${jwt.secret-key}") String secretKey) {
        this.secretKey = secretKey;
    }

    public String createRefreshToken(String subject) {
        return String.format("%s-%s", UUID.randomUUID(), subject);
    }

    /**
     * 토큰 생성 메소드
     *
     * @param subject        : 토큰 생성에 첨가할 User 정보
     * @param EXPIRE_TIME : 토큰 만료 기간
     * @return : 생성된 Jwt 토큰 문자열
     */
    @Override
    public String createToken(String subject) {
        Key key = getSecretKey();
        // PayLoad 구성
        Claims claims = Jwts.claims()
                .setSubject(subject) // 토큰 생성 시 사용하는 내용
                .setIssuedAt(new Date()) // 생성 시각(초까지)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME)); // 만료기간

        return Jwts.builder()
                .setHeaderParam("typ", "JWT") // 헤더 구성
                .setHeaderParam("alg", key.getAlgorithm()) // 헤더 구성
                .setClaims(claims) // PayLoad 구성
                .signWith(key, SignatureAlgorithm.HS512) // Signature 구성
                .compact(); // 합체
    }

    /**
     * 토큰 검증 메소드
     *
     * @param token : 검증할 토큰
     */
    @Override
    public void validateToken(String token) {
        try {
            // parseClaimsJws : Claims의 서명으로 토큰의 유효성 검증 & 토큰의 유효기간 검증
            Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token);
        } catch (SignatureException ex) {
//            log.warn("{}", ex.getMessage(), ex);
            throw UnAuthorizationException.withDetail("토큰 검증이 실패하였습니다!");
        } catch (ExpiredJwtException ex) {
//            log.warn("{}", ex.getMessage(), ex);
            throw UnAuthorizationException.withDetail("토큰 유효기간이 만료되었습니다!");
        }
    }


    /**
     * 토큰 생성시 기입했던 Claims를 반환하는 메소드
     *
     * @param token : Claims를 꺼내올 토큰
     * @return Claim
     */

    @Override
    public String getSubject(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (SignatureException ex) {
            throw UnAuthorizationException.withDetail("토큰 검증이 실패하였습니다!");
        } catch (ExpiredJwtException ex) {
            throw UnAuthorizationException.withDetail("토큰 유효기간이 만료되었습니다!");
        }
    }

    /**
     * Jws 서명에 사용할 Key를 생성하는 메소드.
     * byte[]로 문자열 형태의 Key를 디코딩 후 hash 함수를 통해 key 생성.
     *
     * @return Key 객체
     */
    private SecretKey getSecretKey() {
        byte[] byteKey = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(byteKey);
    }
}
