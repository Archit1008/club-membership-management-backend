
package com.hcl.user.webconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS") // ✅ add PATCH
                        .allowedHeaders("Authorization", "Content-Type", "Accept")         // ✅ ensure auth header
                        .exposedHeaders("Authorization")                                   // optional
                        .allowCredentials(true)                                            // if you need cookies (not typical for JWT)
                        .maxAge(3600);                                                     // cache preflight 1h
            }
        };
    }
}
