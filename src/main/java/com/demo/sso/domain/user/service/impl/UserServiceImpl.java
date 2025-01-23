package com.demo.sso.domain.user.service.impl;

import com.demo.sso.domain.user.dao.UserRepository;
import com.demo.sso.domain.user.dto.*;
import com.demo.sso.domain.user.exception.*;
import com.demo.sso.global.auth.jwt.*;
import com.demo.sso.domain.user.dao.UserTokenRepository;
import com.demo.sso.domain.user.domain.User;
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
        List<User> appUsers = userRepository.findAllByApplication(request.getApplication());
        // 계정, 폰 중복 검사
        validateDuplicate(request.getAccount(), request.getPhone(), appUsers);
        User user = User.create(request, passwordEncoder);
        userRepository.save(user);
        return user.getId();
    }


    @Transactional(readOnly = true)
    void validateDuplicate(String account, String phone, List<User> appUsers) {
        for (User user : appUsers) {
            if (user.getAccount().equals(account)) throw DuplicateAccountException.withDetail(account);
            if (user.getPhone().equals(phone)) throw DuplicatePhoneException.withDetail(phone);
        }

    }

    @Transactional(readOnly = true)
    User findByAccount(String account, String application) {
        List<User> appUsers = userRepository.findAllByApplication(application);
        return appUsers.stream()
                .filter(u -> u.getAccount().equals(account))
                .findFirst()
                .orElseThrow(() -> AccountNotFoundException.withDetail(account));
    }


    @Transactional(readOnly = true)
    User findByKakaoAccount(String kakaoAccount, String application) {
        List<User> appUsers = userRepository.findAllByApplication(application);
        return appUsers.stream()
                .filter(u -> kakaoAccount.equals(u.getKakaoAccount()))
                .findFirst()
                .orElseThrow(() -> KakaoAccountNotFoundException.withDetail(kakaoAccount));
    }

    @Transactional(readOnly = true)
    User findByPhone(String phone, String application) {
        List<User> appUsers = userRepository.findAllByApplication(application);
        return appUsers.stream()
                .filter(u -> u.getPhone().equals(phone))
                .findFirst()
                .orElseThrow(() -> PhoneNotFoundException.withDetail(String.format("어플리케이션: %s 폰 번호 : %s", application, phone)));
    }

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

    @Override
    public void logout(Long userId) {
        userTokenRepository.deleteById(userId);
    }

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
    public KakaoLoginResponse kakaoLoginV1(KakaoLoginParams params) {
        String kakaoAccessToken = kakaoApiClient.requestAccessToken(params);
        KakaoInfoResponse kakaoInfoResponse = kakaoApiClient.requestUserInfo(kakaoAccessToken);
        String application = params.getApplication();
        UserInfoDto userInfoDto = new UserInfoDto(kakaoInfoResponse);
        String accessToken = null;
        String refreshToken = null;

        String kakaoAccount = userInfoDto.getKakaoAccount();
        // 회원 DB에 있다면 token만 전달
        try {
            User user = findByKakaoAccount(kakaoAccount, application);
            accessToken = jwtProvider.createToken(String.valueOf(user.getId()));
            refreshToken = userTokenProvider.createToken(String.valueOf(user.getId()));
            // refresh Token 토큰 저장.
            userTokenRepository.save(UserToken.create(user.getId(), refreshToken));
        } catch (KakaoAccountNotFoundException ex) {
            // 해당 카카오 계정의 User가 없을 경우 -> 가입 처리
        }
        return new KakaoLoginResponse(accessToken, refreshToken, userInfoDto);
    }

    @Override
    public KakaoLoginResponse kakaoLoginV2(KakaoLoginParams params) {
        String kakaoAccessToken = kakaoApiClient.requestAccessToken(params);
        return kakaoLoginByApp(
                new KakaoTokenRequest(kakaoAccessToken, params.getApplication())
        );
    }

    @Override
    public KakaoLoginResponse kakaoLoginByApp(KakaoTokenRequest request) {
        KakaoInfoResponse kakaoInfoResponse = kakaoApiClient.requestUserInfo(request.getToken());
        String application = request.getApplication();
        UserInfoDto userInfoDto = new UserInfoDto(kakaoInfoResponse);
        String accessToken = null;
        String refreshToken = null;

        String phone = userInfoDto.getPhone();
        // 회원 DB에 있다면 token만 전달
        try {
            User user = findByPhone(phone, application);
            user.setKakaoAccount(userInfoDto.getKakaoAccount());
            accessToken = jwtProvider.createToken(String.valueOf(user.getId()));
            refreshToken = userTokenProvider.createToken(String.valueOf(user.getId()));
            // refresh Token 토큰 저장.
            userTokenRepository.save(UserToken.create(user.getId(), refreshToken));
        } catch (PhoneNotFoundException ex) {
            // 해당 핸드폰의 User가 없을 경우 -> 가입 처리
        }
        return new KakaoLoginResponse(accessToken, refreshToken, userInfoDto);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse findUser(Long userId) {
        User user = this.userRepository.findOne(userId);
        return new UserResponse(user);
    }

    @Override
    public UserResponse updateUser(Long userId, UpdateUserRequest request) {
        User user = this.userRepository.findOne(userId);
        List<User> targetUsers = this.userRepository.findAllByApplication(user.getApplication())
                .stream()
                .filter((appUser) -> user != appUser)
                .toList();
        validateDuplicate(request.getAccount(), request.getPhone(), targetUsers);
        user.update(request);
        return new UserResponse(user);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = this.userRepository.findOne(userId);
        this.userRepository.delete(user);
    }

    @Override
    public List<User> findUsersById(SimpleUserRequest request) {
        return this.userRepository.findAllByIdList(request.getUsers());
    }

    @Override
    public List<User> findUsersByName(SearchUserRequest request) {
        return this.userRepository.findAllByIdList(request.getUsers()).stream()
                .filter(u -> u.getName().startsWith(request.getName()))
                .toList();

    }

}
