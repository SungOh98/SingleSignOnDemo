package com.demo.sso.domain.user.dto;

import com.demo.sso.domain.user.domain.User;
import lombok.Data;

@Data
public class UserInfoResponse {

    public UserInfoResponse(User user) {
        this.userName = user.getName();
        this.password = user.getPassword();
    }
    private String userName;
    private String password;
}
