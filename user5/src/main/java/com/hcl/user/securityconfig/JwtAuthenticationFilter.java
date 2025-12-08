package com.hcl.user.securityconfig;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.var;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hcl.user.serviceImpl.MyUserService;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final MyUserService myUserService;

	public JwtAuthenticationFilter(JwtUtil jwtUtil, MyUserService myUserService) {
		this.jwtUtil = jwtUtil;
		this.myUserService = myUserService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// ✅ 1. Skip token check for authentication endpoints
		String path = request.getServletPath();
		if (path.startsWith("/api/auth")) {
			filterChain.doFilter(request, response);
			return;
		}

		// ✅ 2. Normal token validation for other endpoints
		final String authHeader = request.getHeader("Authorization");
		String token = null;
		String username = null;

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
			username = jwtUtil.extractUsername(token);
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = myUserService.loadUserByUsername(username);
			if (jwtUtil.validateToken(token, userDetails)) {
				var authorities = jwtUtil.extractAuthorities(token);
				System.out.println("Authorities from token: : " + authorities);
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, jwtUtil.extractAuthorities(token));
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}

		filterChain.doFilter(request, response);
	}
}
