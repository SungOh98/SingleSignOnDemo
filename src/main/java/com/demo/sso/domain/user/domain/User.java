package com.demo.sso.domain.user.domain;

import com.demo.sso.domain.BaseEntity;
import com.demo.sso.domain.hospital.domain.Hospital;
import com.demo.sso.domain.user.dto.SignUpRequest;
import com.demo.sso.domain.user.dto.UpdateUserRequest;
import com.demo.sso.global.util.encrytion.CryptoConverter;
import com.demo.sso.global.util.encrytion.OnlyEncryptionConverter;
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

    /*==필수 속성들(암/복호화 모두 수행)==*/
    @Convert(converter = CryptoConverter.class)
    private String account;
    private String password;
    @Convert(converter = CryptoConverter.class)
    private String phone;
    private String userType;
    private String application;


    /*==Optional 속성들(복호화는 안함.)==*/
    @Convert(converter = CryptoConverter.class)
    private String name;
    @Convert(converter = CryptoConverter.class)
    private String nickname;
    @Convert(converter = CryptoConverter.class)
    private String gender;
    @Convert(converter = CryptoConverter.class)
    @Column(name = "birthyear")
    private String birthYear;
    @Convert(converter = CryptoConverter.class)
    private String height;
    @Enumerated(EnumType.STRING)
    private Language language = Language.ko;
    private Boolean isActive;
    private Boolean alarmAvailable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    public static User create(SignUpRequest request, BCryptPasswordEncoder passwordEncoder) {
        User user = new User();
        user.account = request.getAccount();
        user.password = passwordEncoder.encode(request.getPassword());
        user.name = request.getName();
        user.nickname = request.getNickname();
        user.phone = request.getPhone();
        user.userType = request.getUserType();
        user.application = request.getApplication();
        user.setLanguage(request.getLanguage());
        user.isActive = true;
        user.alarmAvailable = true;
        user.birthYear = String.valueOf(request.getBirthYear());
        user.height = String.valueOf(request.getHeight());
        user.gender = String.valueOf(request.getGender());
        user.updateName();
        return user;
    }

    public void update(UpdateUserRequest request) {
        this.account = request.getAccount() != null ? request.getAccount() : this.account;
        this.name = request.getName() != null ? request.getName() : this.name;
        this.nickname = request.getNickname() != null ? request.getNickname() : this.nickname;
        this.phone = request.getPhone() != null ? request.getPhone() : this.phone;
        this.gender = request.getGender() != null ? request.getGender() : this.gender;
        this.birthYear = request.getBirthYear() != null ? request.getBirthYear() : this.birthYear;
        this.height = request.getHeight() != null ? request.getHeight() : this.height;
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
