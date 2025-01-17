package com.demo.sso.domain.user.domain;

import com.demo.sso.domain.BaseEntity;
import com.demo.sso.domain.user.dto.SignUpRequest;
import com.demo.sso.domain.user.dto.UpdateUserRequest;
import com.demo.sso.global.util.encrytion.EncryptedGenderConverter;
import com.demo.sso.global.util.encrytion.EncryptedIntegerConverter;
import com.demo.sso.global.util.encrytion.EncryptedLocalDateConverter;
import com.demo.sso.global.util.encrytion.EncryptedStringConverter;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.*;

@Getter
@Entity
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Convert(converter = EncryptedStringConverter.class)
    private String account;
    @Convert(converter = EncryptedStringConverter.class)
    @Column(name = "kakao_account")
    private String kakaoAccount;
    private String password;
    @Convert(converter = EncryptedStringConverter.class)
    private String phone;

    private String userType;
    private String application;

    @Convert(converter = EncryptedStringConverter.class)
    private String name;
    @Convert(converter = EncryptedStringConverter.class)
    private String nickname;
    @Convert(converter = EncryptedGenderConverter.class)
    private Gender gender;
    @Convert(converter = EncryptedLocalDateConverter.class)
    @Column(name = "birthyear")
    private LocalDate birthYear;
    @Convert(converter = EncryptedIntegerConverter.class)
    private Integer height;
    @Enumerated(EnumType.STRING)
    private Language language = Language.ko;
    private Boolean isActive;
    private Boolean alarmAvailable;
    @Column(name = "push_token")
    private String pushToken;


    public static User create(SignUpRequest request, BCryptPasswordEncoder passwordEncoder) {
        User user = new User();
        user.account = request.getAccount();
        user.kakaoAccount = request.getKakaoAccount();
        user.password = passwordEncoder.encode(request.getPassword());
        user.name = request.getName();
        user.nickname = request.getNickname();
        user.phone = request.getPhone();
        user.userType = request.getUserType();
        user.application = request.getApplication();
        user.setLanguage(request.getLanguage());
        user.isActive = true;
        user.alarmAvailable = true;
        user.birthYear = request.getBirthYear();
        user.height = request.getHeight();
        user.gender = request.getGender();
        user.updateName();
        return user;
    }

    public void update(UpdateUserRequest request) {
        this.account = request.getAccount() != null ? request.getAccount() : this.account;
        this.kakaoAccount = request.getKakaoAccount() != null ? request.getKakaoAccount() : this.kakaoAccount;
        this.name = request.getName() != null ? request.getName() : this.name;
        this.nickname = request.getNickname() != null ? request.getNickname() : this.nickname;
        this.phone = request.getPhone() != null ? request.getPhone() : this.phone;
        this.gender = request.getGender() != null ? request.getGender() : this.gender;
        this.birthYear = request.getBirthYear() != null ? request.getBirthYear() : this.birthYear;
        this.height = request.getHeight() != null ? request.getHeight() : this.height;
        this.language = request.getLanguage() != null ? request.getLanguage() : this.language;
        this.isActive = request.getIsActive() != null ? request.getIsActive() : this.isActive;
        this.alarmAvailable = request.getAlarmAvailable() != null ? request.getAlarmAvailable() : this.alarmAvailable;
        this.pushToken = request.getPushToken() != null ? request.getPushToken() : this.pushToken;
    }


    public void setLanguage(Language language) {
        if (language != null) this.language = language;
        else this.language = Language.ko;
    }

    public void updateName() {
        if (name == null) name = account;
        if (nickname == null) nickname = name;
    }

    public boolean isSamePassword(String inputPassword, BCryptPasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(inputPassword, password);
    }


}
