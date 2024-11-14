package com.demo.sso.domain.user.domain;

import com.demo.sso.domain.BaseEntity;
import com.demo.sso.domain.user.dto.SignUpRequest;
import com.demo.sso.global.infra.kakao.KakaoInfoResponse;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static jakarta.persistence.GenerationType.*;

@Getter
@Entity
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String account;
    private String name;
    private String nickname;
    private String phone;
    private String password;
    private String application;
    private String userType;

    @Enumerated(EnumType.STRING)
    private Language language = Language.ko;

    public void updateName() {
        if (name == null) name = account;
        if (nickname == null) nickname = name;
    }

    public static User createByKakaoInfo(KakaoInfoResponse kakaoInfoResponse) {
        User user = new User();
        user.account = kakaoInfoResponse.getAccount();
        user.name = kakaoInfoResponse.getName();
        user.nickname = kakaoInfoResponse.getNickName();
        user.phone = kakaoInfoResponse.getPhoneNumber();

        return user;
    }

    public static User create(SignUpRequest request, BCryptPasswordEncoder passwordEncoder) {
        User user = new User();
        user.account = request.getAccount();
        user.name = request.getName();
        user.nickname = request.getNickname();
        user.phone = request.getPhone();
        user.password = passwordEncoder.encode(request.getPassword());
        return user;
    }

    public boolean isSamePassword(String inputPassword, BCryptPasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(inputPassword, password);
    }


}
