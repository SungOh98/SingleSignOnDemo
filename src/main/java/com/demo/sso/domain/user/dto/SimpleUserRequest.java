package com.demo.sso.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class SimpleUserRequest {
    @NotNull
    @Schema(description = "정보를 얻을 User ID 목록", example = "[1, 2, 3, 4]")
    private List<Long> users;
}
