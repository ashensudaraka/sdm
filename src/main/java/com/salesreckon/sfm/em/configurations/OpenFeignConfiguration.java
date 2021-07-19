package com.salesreckon.sfm.em.configurations;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class OpenFeignConfiguration {
    private final HttpServletRequest request;

    @Bean
    public RequestInterceptor tokenInjectionInterceptor() {
        return new RequestInterceptor() {
            public void apply(RequestTemplate template) {
                template.header(HttpHeaders.AUTHORIZATION, request.getHeader(HttpHeaders.AUTHORIZATION));
            }
        };
    }
}
