package com.demo.sso.domain.user.dto;

import com.demo.sso.domain.user.domain.User;
import lombok.Data;

import java.util.List;

@Data
public class UsersResponse {
    private List<UserResponse> users;

    public UsersResponse(List<User> users) {
        this.users = users.stream()
                .filter(User::getIsActive)
                .map(UserResponse::new)
                .toList();
    }
}
