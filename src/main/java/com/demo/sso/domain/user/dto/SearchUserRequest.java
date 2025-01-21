package com.demo.sso.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class SearchUserRequest {
    @Schema(description = "정보를 얻을 User ID 목록", example = "[1, 2, 3, 4]")
    private List<Long> users;
    @Schema(description = "유저 이름", example = "홍")
    private String name;
}
