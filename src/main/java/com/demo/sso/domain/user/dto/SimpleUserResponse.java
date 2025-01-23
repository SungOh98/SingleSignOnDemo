package com.demo.sso.domain.user.dto;

import com.demo.sso.domain.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class SimpleUserResponse {
    @Schema(description = "유저의 간단한 정보를 담은 목록")
    private List<SimpleUserDto> users;

    public SimpleUserResponse(List<User> users) {
        this.users = users.stream()
                .map(SimpleUserDto::new)
                .collect(Collectors.toList());
    }
}
