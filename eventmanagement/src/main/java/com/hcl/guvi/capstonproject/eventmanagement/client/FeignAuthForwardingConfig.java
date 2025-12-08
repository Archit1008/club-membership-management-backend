package com.hcl.guvi.capstonproject.eventmanagement.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;

@Configuration
public class FeignAuthForwardingConfig {

 @Bean
 public RequestInterceptor authForwardingInterceptor() {
     return template -> {
         ServletRequestAttributes attrs =
             (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
         if (attrs != null) {
             String auth = attrs.getRequest().getHeader(HttpHeaders.AUTHORIZATION);
             if (auth != null && !auth.isBlank()) {
                 template.header(HttpHeaders.AUTHORIZATION, auth);
             }
         }
     };
 }
}
