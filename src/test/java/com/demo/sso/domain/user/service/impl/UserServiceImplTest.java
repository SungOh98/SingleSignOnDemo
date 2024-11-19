package com.demo.sso.domain.user.service.impl;

import com.demo.sso.domain.user.dao.UserRepository;
import com.demo.sso.domain.user.domain.Language;
import com.demo.sso.domain.user.domain.User;
import com.demo.sso.domain.user.dto.LoginRequest;
import com.demo.sso.domain.user.dto.SignUpRequest;
import com.demo.sso.domain.user.exception.AccountNotFoundException;
import com.demo.sso.domain.user.exception.DuplicateAccountException;
import com.demo.sso.domain.user.exception.DuplicatePhoneException;
import com.demo.sso.domain.user.service.UserService;
import com.demo.sso.global.auth.exception.UnAuthenticationException;
import com.demo.sso.global.auth.jwt.AuthTokens;
import com.demo.sso.global.auth.jwt.JwtProvider;
import com.demo.sso.global.auth.jwt.UserTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserTokenProvider userTokenProvider;

    @Test
    public void 유저_회원가입() throws Exception {
        //given
        SignUpRequest request = new SignUpRequest(
                "patientA",
                "patientA",
                null, null, "010-1234-5678",
                "doctor", "app1", null
        );

        //when
        Long userId = userService.totalSignUp(request);
        User user = userRepository.findOne(userId);

        //then
        assertNotNull(user);
        assertEquals(request.getAccount(), user.getAccount());
        assertTrue(user.isSamePassword(request.getPassword(), passwordEncoder));
        assertEquals(request.getAccount(), user.getName());
        assertEquals(request.getAccount(), user.getNickname());
        assertEquals(request.getPhone(), user.getPhone());
        assertEquals(request.getUserType(), user.getUserType());
        assertEquals(request.getApplication(), user.getApplication());
        assertEquals(Language.ko, user.getLanguage());
    }

    @Test
    public void 계정_폰_중복에러확인() throws Exception {
        //given
        SignUpRequest request1 = new SignUpRequest(
                "patientA",
                "patientA",
                null, null, "010-1234-5678",
                "doctor", "app1", null
        );

        Long userId = userService.totalSignUp(request1);

        SignUpRequest request2 = new SignUpRequest(
                "patientA",
                "patientA",
                null, null, "0",
                "doctor", "app1", null
        );
        SignUpRequest request3 = new SignUpRequest(
                "patientB",
                "patientA",
                null, null, "010-1234-5678",
                "doctor", "app1", null
        );

        //when & then
        assertThrows(
                DuplicateAccountException.class, () -> userService.totalSignUp(request2),
                "계정 중복 에러가 터져야함."
        );
        assertThrows(
                DuplicatePhoneException.class, () -> userService.totalSignUp(request3),
                "폰 중복 에러가 터져야함."
        );

    }
    /**
     * Redis 테스트 환경을 구축해야 테스트가 가능함!!
     **/
//    @Test
//    public void 로그인_토큰_검증() throws Exception {
//        //given
//        SignUpRequest request = new SignUpRequest(
//                "patientA",
//                "patientA",
//                null, null, "010-1234-5678",
//                "doctor", "app1", null
//        );
//
//        Long userId = userService.totalSignUp(request);
//
//        //when
//        AuthTokens tokens = userService.login(new LoginRequest(request.getAccount(), request.getPassword(), request.getApplication()));
//        //then
//        assertEquals(
//                userId, Integer.parseInt(jwtProvider.getSubject(tokens.getAccessToken()))
//        );
//
//        assertEquals(
//                userId,
//                Integer.parseInt(userTokenProvider.getSubject(tokens.getRefreshToken()))
//        );
//
//    }

//    @Test
//    public void 잘못된_로그인_검증() throws Exception{
//        //given
//        SignUpRequest request = new SignUpRequest(
//                "patientA",
//                "patientA",
//                null, null, "010-1234-5678",
//                "doctor", "app1", null
//        );
//
//        Long userId = userService.totalSignUp(request);
//        //when
//        AuthTokens tokens = userService.login(new LoginRequest(request.getAccount(), request.getPassword(), request.getApplication()));
//        LoginRequest request1 = new LoginRequest("patientB", request.getPassword(), request.getApplication());
//        LoginRequest request2 = new LoginRequest(request.getAccount(), "patientB", request.getApplication());
//        LoginRequest request3 = new LoginRequest(request.getAccount(), request.getPassword(), "app2");
//
//        //then
//        assertThrows(AccountNotFoundException.class,
//                () -> userService.login(request1)
//        );
//        assertThrows(UnAuthenticationException.class,
//                () -> userService.login(request2)
//        );
//        assertThrows(AccountNotFoundException.class,
//                () -> userService.login(request3)
//        );
//    }

}