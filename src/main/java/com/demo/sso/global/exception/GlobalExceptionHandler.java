package com.demo.sso.global.exception;

import com.demo.sso.global.auth.exception.*;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

import static com.demo.sso.global.exception.CustomResponseCode.*;


/**
 * 모든 컨트롤러의 예외처리를 담당하는 클래스.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // Validation 예외 처리 메소드
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        log.warn("잘못된 요청 매개변수 에러!  {}", ex.getMessage(), ex);
        // BindingResult 로부터 에러메시지들 뽑아내기
        String errorMessages = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("\n"));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.from(errorMessages));
    }

    @ExceptionHandler(ExpiredCodeException.class)
    public ResponseEntity<ErrorResponse> handleExpiredCodeException(ExpiredCodeException ex) {
        log.warn("SMS 인증 코드 만료 예외 발생 : {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.GONE).body(ErrorResponse.of(ex.getMessage(), SMS_MESSAGE_TIMEOUT));
    }

    @ExceptionHandler(UnValidCodeException.class)
    public ResponseEntity<ErrorResponse> handleUnValidCodeException(UnValidCodeException ex) {
        log.warn("SMS 인증 실패(잘못 입력) 예외 발생 : {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.of(ex.getMessage(), WRONG_SMS_MESSAGE));
    }

    @ExceptionHandler(TooManyRequestException.class)
    public ResponseEntity<ErrorResponse> handleTooManyRequestException(TooManyRequestException ex) {
        log.warn("SMS 인증 과다 요청 예외 발생 : {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(ErrorResponse.of(ex.getMessage(), TOO_MANY_SMS_REQUEST));
    }


    // 예상치 못한 에러 발생 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllException(Exception ex) {
        log.error("예상치 못한 에러 발생!\n {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.from("예상치 못한 에러 발생, 세부사항: " + ex.getMessage()));
    }

    @ExceptionHandler(UnAuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(UnAuthenticationException ex) {
        log.warn("인증 예외 발생 : {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse.from(ex.getMessage()));
    }

    @ExceptionHandler(UnAuthorizationException.class)
    public ResponseEntity<ErrorResponse> handleAuthorizationException(UnAuthorizationException ex) {
        log.warn("인가(권한) 예외 발생 : {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ErrorResponse.from(ex.getMessage()));
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponse> handleJwtException(JwtException ex) {
        log.warn("Jwt 토큰 관련 예외 발생 : {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.of("Jwt 토큰 형식이 맞지 않거나 서명형식이 맞지 않습니다.", INVALID_JWT_TOKEN_TYPE));
    }

    @ExceptionHandler(UserTokenExpiredException.class)
    public ResponseEntity<ErrorResponse> handleUserTokenExpiredException(UserTokenExpiredException ex) {
        log.warn("refresh token 만료 예외 : {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(
                        ErrorResponse.of(ex.getMessage(), REFRESH_TOKEN_TIMEOUT)
                );
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDataNotFoundException(
            DataNotFoundException ex,
            HttpServletRequest request
    ) {
        log.warn("데이터 찾을 수 없는 예외 발생!!\n {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.from(
                ex.getMessage()
        ));
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateException(
            DuplicateException ex,
            HttpServletRequest request
    ) {
        log.warn("데이터 중복 예외 발생!!\n {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponse.from(
                ex.getMessage()
        ));
    }


}
