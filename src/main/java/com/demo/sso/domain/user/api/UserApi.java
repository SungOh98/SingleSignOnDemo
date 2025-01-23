package com.demo.sso.domain.user.api;

import com.demo.sso.domain.user.dto.*;
import com.demo.sso.global.auth.jwt.AdminOnly;
import com.demo.sso.global.auth.jwt.UserOnly;
import com.demo.sso.global.exception.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "유저관련 API", description = "API Gateway 서버가 사용하는 유저 관련 API")
public interface UserApi {

    @Operation(summary = "회원 정보 저장 API(API Gateway 가 사용)", description = "Request Body의 Schema 버튼 눌러서 필수 속성 확인해주세요.")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "회원가입 성공")
    )
    @PostMapping("signup")
    ResponseEntity<TotalSignUpResponse> signup(@RequestBody @Valid SignUpRequest request);

    @Operation(summary = "회원 정보 조회(API Gateway 가 사용)", security = {@SecurityRequirement(name = "bearerAuth")})
    @GetMapping("user")
    ResponseEntity<UserResponse> getUser(@UserOnly Long userId);


    @Operation(summary = "회원 정보 수정(API Gateway 가 사용)", security = {@SecurityRequirement(name = "bearerAuth")})
    @PutMapping("user")
    ResponseEntity<UserResponse> updateUser(@UserOnly Long userId, @RequestBody @Valid UpdateUserRequest request);


    @Operation(summary = "회원 삭제(API Gateway 가 사용)", security = {@SecurityRequirement(name = "bearerAuth")})
    @DeleteMapping("user")
    ResponseEntity<SuccessResponse> deleteUser(@UserOnly Long userId);

    @Operation(summary = "회원 목록 조회(API Gateway가 사용)", security = {@SecurityRequirement(name = "bearerAuth")})
    @PostMapping("users")
    ResponseEntity<SimpleUserResponse> getUsers(@UserOnly Long userId, @RequestBody @Valid SimpleUserRequest request);

    @Operation(summary = "관리자용 회원 목록 조회(API Gateway가 사용)", security = {@SecurityRequirement(name = "bearerAuth")})
    @GetMapping("users")
    ResponseEntity<UsersResponse> getUsers(@AdminOnly Long userId, @RequestParam String application);

    @Operation(summary = "회원 이름으로 목록 조회(API Gateway가 사용)", security = {@SecurityRequirement(name = "bearerAuth")})
    @PostMapping("users/name")
    ResponseEntity<SimpleUserResponse> getUsers(@UserOnly Long userId, @RequestBody @Valid SearchUserRequest request);

}
