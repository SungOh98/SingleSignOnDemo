package com.demo.sso.global.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Client의 요청과 응답을 로깅하기 위한 Filter
 */
@Slf4j
public class ApiLoggingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Filter 초기화 로직 필요시 구현
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String method = httpRequest.getMethod();
        String uri = httpRequest.getRequestURI();
        String clientIp = getClientIp(httpRequest);

        /**
         * 다음 필터로 요청 넘기기
         * - 다음 필터가 없다면 Controller로 요청 전달
         * 필터로 넘기기 전 -> 요청
         * 필터로 넘긴 후 -> 응답
         */
        filterChain.doFilter(servletRequest, servletResponse);

        int status = httpResponse.getStatus();

        log.info("{}, {} {}, {}", clientIp, method, uri, HttpStatus.valueOf(status));
    }

    @Override
    public void destroy() {
        // Filter 종료 로직 필요시 구현
        Filter.super.destroy();
    }

    public String getClientIp(HttpServletRequest request) {
        // Client가 Proxy나 Load Balancer로 요청을 보내는 경우 원래 클라이언트의 IP는 X-Forwarded-For 헤더에 담김
        String ipAddress = request.getHeader("X-Forwarded-For");

        // Proxy-Client-IP에서 Client의 IP를 꺼내옴
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        // WL~에서 꺼내옴
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        // Proxy나 Load Balancer를 사용하지 않은 경우, 직접 꺼내옴
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }
}
