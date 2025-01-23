package com.demo.sso.domain.user.api;

import com.demo.sso.domain.user.dao.UserRepository;
import com.demo.sso.domain.user.domain.User;
import com.demo.sso.domain.user.dto.*;
import com.demo.sso.domain.user.service.UserService;
import com.demo.sso.global.auth.jwt.AdminOnly;
import com.demo.sso.global.auth.jwt.UserOnly;
import com.demo.sso.global.exception.SuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class UserController implements UserApi {
    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("user")
    public ResponseEntity<TotalSignUpResponse> signup(@RequestBody @Valid SignUpRequest request) {
        Long userId = userService.totalSignUp(request);
        return ResponseEntity.ok(new TotalSignUpResponse(userId));
    }

    @GetMapping("user")
    public ResponseEntity<UserResponse> getUser(@UserOnly Long userId) {
        UserResponse user = this.userService.findUser(userId);
        return ResponseEntity.ok(user);
    }

    @PutMapping("user")
    public ResponseEntity<UserResponse> updateUser(@UserOnly Long userId, @RequestBody @Valid UpdateUserRequest request) {
        log.info("{}", request);
        UserResponse userResponse = this.userService.updateUser(userId, request);
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping("user")
    public ResponseEntity<SuccessResponse> deleteUser(@UserOnly Long userId) {
        this.userService.deleteUser(userId);
        return ResponseEntity.ok(SuccessResponse.from("success"));
    }

    @PostMapping("users")
    public ResponseEntity<SimpleUserResponse> getUsers(@UserOnly Long userId, @RequestBody @Valid SimpleUserRequest request) {
        List<User> users = this.userService.findUsersById(request);
        return ResponseEntity.ok(new SimpleUserResponse(users));
    }

    @GetMapping("users")
    public ResponseEntity<UsersResponse> getUsers(@AdminOnly Long userId, @RequestParam String application) {
        List<User> users = userRepository.findAllByApplication(application);
        return ResponseEntity.ok(new UsersResponse(users));
    }

    @PostMapping("users/name")
    public ResponseEntity<SimpleUserResponse> getUsers(@UserOnly Long userId, @RequestBody @Valid SearchUserRequest request) {
        List<User> users = this.userService.findUsersByName(request);
        return ResponseEntity.ok(new SimpleUserResponse(users));
    }


}
