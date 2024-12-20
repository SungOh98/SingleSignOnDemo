package com.demo.sso.domain.user.service.impl;

import com.demo.sso.domain.user.dao.UserRepository;
import com.demo.sso.domain.user.dto.*;
import com.demo.sso.domain.user.exception.DuplicatePhoneException;
import com.demo.sso.global.auth.jwt.*;
import com.demo.sso.domain.user.dao.UserTokenRepository;
import com.demo.sso.domain.user.domain.User;
import com.demo.sso.domain.user.exception.AccountNotFoundException;
import com.demo.sso.domain.user.exception.DuplicateAccountException;
import com.demo.sso.domain.user.service.UserService;
import com.demo.sso.global.auth.exception.UnAuthenticationException;
import com.demo.sso.global.infra.kakao.KakaoApiClient;
import com.demo.sso.global.infra.kakao.KakaoInfoResponse;
import com.demo.sso.global.infra.kakao.KakaoLoginParams;
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
    private final KakaoApiClient kakaoApiClient;

    @Override
    public Long totalSignUp(SignUpRequest request) {
        // 계정, 폰 중복 검사
        validateDuplicate(request.getAccount(), request.getPhone(), request.getApplication());
        User user = User.create(request, passwordEncoder);
        userRepository.save(user);
        return user.getId();
    }


    @Transactional(readOnly = true)
    void validateDuplicate(String account, String phone, String application) {
        /**
         ** 암호화 전 로직 **
         if (!userRepository.findAllByAccount(account, application).isEmpty())
         throw DuplicateAccountException.withDetail(account);
         if (!userRepository.findAllByPhone(phone, application).isEmpty())
         throw DuplicatePhoneException.withDetail(phone);
         *
         */
        List<User> appUsers = userRepository.findAllByApplication(application);
        for (User user : appUsers) {
            if (user.getAccount().equals(account)) throw DuplicateAccountException.withDetail(account);
            if (user.getPhone().equals(phone)) throw DuplicatePhoneException.withDetail(phone);
        }

    }

    /**
     * 계정으로 User 찾기 메소드
     * DB 암호화로 인해 JPQL로 찾을 수 없음(SELECT 절에 WHERE 절 사용 불가!)
     *
     * @param account : 찾을 계정
     * @return user :  Entity
     */
    @Transactional(readOnly = true)
    User findByAccount(String account, String application) {
        List<User> appUsers = userRepository.findAllByApplication(application);
        return appUsers.stream()
                .filter(u -> u.getAccount().equals(account))
                .findFirst()
                .orElseThrow(() -> AccountNotFoundException.withDetail(account));
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

        User findUser = findByAccount(request.getAccount(), request.getApplication());

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

    /**
     * 카카오 로그인
     *
     * @param params : 카카오서버에서 받은 인증 코드가 담긴 객
     * @return
     */
    @Override
    public KakaoLoginResponse kakaoLogin(KakaoLoginParams params) {
        String kakaoAccessToken = kakaoApiClient.requestAccessToken(params);
        KakaoInfoResponse kakaoInfoResponse = kakaoApiClient.requestUserInfo(kakaoAccessToken);
        String account = kakaoInfoResponse.getAccount();
        String application = params.getApplication();
        UserInfoDto userInfoDto = new UserInfoDto(kakaoInfoResponse);
        String accessToken = null;
        String refreshToken = null;
        // 회원 DB에 있다면 token만 전달
        try {
            User user = findByAccount(account, application);
            accessToken = jwtProvider.createToken(String.valueOf(user.getId()));
            refreshToken = userTokenProvider.createToken(String.valueOf(user.getId()));
            // refresh Token 토큰 저장.
            userTokenRepository.save(UserToken.create(user.getId(), refreshToken));
        } catch (AccountNotFoundException ex) {
            // 회원 DB에 없다면 회원 정보만 전달 => 프론트에서 회원 가입 처리 + 로그인
        }
        return new KakaoLoginResponse(accessToken, refreshToken, userInfoDto);
    }


}
