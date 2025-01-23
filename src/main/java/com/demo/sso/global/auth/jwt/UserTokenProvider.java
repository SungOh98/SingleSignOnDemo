package com.demo.sso.global.auth.jwt;

import com.demo.sso.domain.user.dao.UserTokenRepository;
import com.demo.sso.global.auth.exception.UnAuthorizationException;
import com.demo.sso.global.auth.exception.UserTokenExpiredException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserTokenProvider implements TokenProvider{

    private final UserTokenRepository userTokenRepository;
    @Override
    public String createToken(String subject) {
        return String.format("%s-%s", UUID.randomUUID(), subject);
    }

    @Override
    public String getSubject(String token) {
        String[] tmp = token.split("-");
        return tmp[tmp.length - 1];
    }

    /**
     * Refresh Token 검증
     * 1. 변조여부확인
     * 2. 만료여부확인 => 만료되면 자동 삭제됨.
     * 식제 시나리오 = (로그아웃, 토큰 불일치, 만료)
     * @param token
     */
    @Override
    public void validateToken(String token) {
        Long userId = Long.parseLong(this.getSubject(token));
        UserToken userToken = userTokenRepository.findById(userId).orElseThrow(
                () -> new UserTokenExpiredException("토큰만료") // 401
        );
        if (!Objects.equals(token, userToken.getRefreshToken())) {
            userTokenRepository.deleteById(userId);
            log.warn("{}", "<Refresh 지워버림!!!!!!!!>");
            throw UnAuthorizationException.withDetail("재로그인"); // 403
        }



    }
}
