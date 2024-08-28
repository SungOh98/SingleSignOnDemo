package com.demo.sso.global.exception;

import com.demo.sso.global.auth.exception.UnAuthenticationException;
import com.demo.sso.global.auth.exception.UnAuthorizationException;
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
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.from("Jwt 토큰 형식이 맞지 않거나 서명형식이 맞지 않습니다."));
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDataNotFoundException(
            DataNotFoundException ex,
            HttpServletRequest request
    ) {
        log.warn("데이터 찾을 수 없는 예외 발생!!\n {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ErrorResponse.from(
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
