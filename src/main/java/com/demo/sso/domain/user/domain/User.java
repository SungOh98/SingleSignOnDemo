package com.demo.sso.domain.user.domain;

import com.demo.sso.domain.user.dto.SignUpRequest;
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

    private String userType;

    public void updateName() {
        if (name == null) name = account;
        if (nickname == null) nickname = name;
    }

    public static User createUser(SignUpRequest request, BCryptPasswordEncoder passwordEncoder) {
        User user = new User();
        user.account = request.getAccount();
        user.name = request.getName();
        user.nickname = request.getNickname();
        user.phone = request.getPhone();
        user.password = passwordEncoder.encode(request.getPassword());
        user.userType = "user";
        return user;
    }

    public boolean isSamePassword(String inputPassword, BCryptPasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(inputPassword, password);
    }


}
