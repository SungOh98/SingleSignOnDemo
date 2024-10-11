package com.demo.sso.global.config;

import com.demo.sso.global.filter.ApiLoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    // ApiLoggingFilter를 등록하는 Beam
    @Bean
    public FilterRegistrationBean<ApiLoggingFilter> filterRegistrationBean() {
        FilterRegistrationBean<ApiLoggingFilter> filterRegistrationBean = new FilterRegistrationBean<>();

        filterRegistrationBean.setFilter(new ApiLoggingFilter());
        // 모든 API에 적용
        filterRegistrationBean.addUrlPatterns("/*");
        // 필터 순서 적용
//        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;

    }
}
