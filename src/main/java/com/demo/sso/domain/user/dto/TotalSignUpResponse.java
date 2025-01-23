package com.demo.sso.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TotalSignUpResponse {
    @Schema(
            description = "회원가입한 User의 user_id",
            example = "1")
    private Long id;
}
