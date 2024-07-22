package com.demo.sso.domain.user.service.impl;

import com.demo.sso.domain.user.dao.UserRepository;
import com.demo.sso.global.auth.jwt.*;
import com.demo.sso.domain.user.dao.UserTokenRepository;
import com.demo.sso.domain.user.domain.User;
import com.demo.sso.domain.user.dto.LoginRequest;
import com.demo.sso.domain.user.dto.SignUpRequest;
import com.demo.sso.domain.user.dto.TokenRefreshRequest;
import com.demo.sso.domain.user.dto.TokenRefreshResponse;
import com.demo.sso.domain.user.exception.AccountNotFoundException;
import com.demo.sso.domain.user.exception.DuplicateAccountException;
import com.demo.sso.domain.user.service.UserService;
import com.demo.sso.global.auth.exception.UnAuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final JwtProvider jwtProvider;
    private final UserTokenProvider userTokenProvider;
    private final UserRepository userRepository;
    private final UserTokenRepository userTokenRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    /**
     * 회원 가입 프로세스
     * 1. 계정 중복 검증.
     * 2. 인증(SMS) -> 일단 생략
     * 3. User Save
     *
     * @param request : 회원 가입 정보
     * @return : 회원 가입 성공한 User의 ID
     */
    @Override
    public Long signUp(SignUpRequest request) {
        // 중복 계정있는지 검증.
        validateDuplicate(request.getAccount());
        // 추후 문자 인증 로직 추가해야함.
        User user = User.createUser(request, passwordEncoder);
        userRepository.save(user);
        return user.getId();
    }

    @Transactional(readOnly = true)
    void validateDuplicate(String account) {
        if (userRepository.findAllByAccount(account).size() > 0) throw DuplicateAccountException.withDetail(account);
    }

    @Override
    public AuthTokens loginByOauth() {
        return null;
    }

    /**
     * 일반 로그인 프로세스
     * 1. 계정있는지 검사
     * 2. 패스워드 검증
     * 3. accessToken, refreshToken 발급
     * 4. refreshToken DB 저장
     * 5. 클라이언트에게 Token 전달
     *
     * @return Access, Refresh 토큰
     */
    @Override
    public AuthTokens login(LoginRequest request) {
        User findUser;
        try {
            findUser = userRepository.findAllByAccount(request.getAccount()).get(0);
        } catch (IndexOutOfBoundsException ex) {
            throw AccountNotFoundException.withDetail(request.getAccount());
        }
        if (!findUser.isSamePassword(request.getPassword(), passwordEncoder)) {
            throw UnAuthenticationException.withDetail("비밀번호가 틀렸습니다.");
        }

        String accessToken = jwtProvider.createToken(
                String.valueOf(findUser.getId())
        );
        String refreshToken = userTokenProvider.createToken(
                String.valueOf(findUser.getId())
        );
        userTokenRepository.save(UserToken.create(findUser.getId(), refreshToken));
        return new AuthTokens(accessToken, refreshToken);

    }

    /**
     * 로그아웃 프로세스
     * 1. refresh 토큰 지우기
     * 2.
     */
    @Override
    public void logout(String token) {
        Long userId = Long.parseLong(jwtProvider.getSubject(token));
        userTokenRepository.deleteById(userId);
        userTokenRepository.addTokenToBlackList(userId, token);
    }

    /**
     * Token 재발급 프로세스.
     * 1. refresh 토큰 검증(위 변조, 유효기간 만료)
     * 2. DB에 저장된 refreshToken과 비교
     * 3. 일치하지 않는다면 거부하고 기존에 DB에 있던 Token 폐기
     * 4. AccessToken, RefreshToken 재발급.
     * 5. RefreshToken DB에 갱신(RTR)
     *
     * @param request : refreshToken과 AccessToken
     * @return : 새로운 refreshToken과 AccessToken
     */
    @Override
    public TokenRefreshResponse refresh(TokenRefreshRequest request) {
        String token = request.getRefreshToken();
        userTokenProvider.validateToken(token);

        Long userId = Long.parseLong(userTokenProvider.getSubject(token));

        String accessToken = jwtProvider.createToken(String.valueOf(userId));
        String refreshToken = userTokenProvider.createToken(String.valueOf(userId));

        userTokenRepository.save(UserToken.create(userId, refreshToken));

        return new TokenRefreshResponse(accessToken, refreshToken);
    }


    @Override
    public List<User> users(String accessToken) {
        jwtProvider.validateToken(accessToken);
        return userRepository.findAll();
    }


}
