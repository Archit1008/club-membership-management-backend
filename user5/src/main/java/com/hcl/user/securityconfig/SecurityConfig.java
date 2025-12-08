package com.hcl.user.securityconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.hcl.user.serviceImpl.MyUserService;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	private final MyUserService myUserService;
	private final JwtUtil jwtUtil;

	public SecurityConfig(MyUserService myUserService, JwtUtil jwtUtil) {
		this.myUserService = myUserService;
		this.jwtUtil = jwtUtil;
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

	    JwtAuthenticationFilter jwtFilter = new JwtAuthenticationFilter(jwtUtil, myUserService);

	    http
	        .cors() // ✅ Enable CORS
	        .and()
	        .csrf().disable()
	        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	        .and()
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/api/auth/**").permitAll() // registration & login
	            .anyRequest().authenticated()
	        )
	        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

	    return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
