package com.hcl.user.securityconfig;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private final Key key;
//	private final long jwtExpirationMs = 1000 * 60 * 60 * 10; // 10 hours
	private final long jwtExpirationMs = 1000 * 60 * 2; // 2 minutes

	public JwtUtil() {
		String secret = Base64.getEncoder().encodeToString("my-super-secret-key-which-is-long-enough".getBytes()); // valid base64 32 bytes
		key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
	}

	public String generateToken(UserDetails userDetails) {
		System.out.println("token method generated");
		List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());

		return Jwts.builder().setSubject(userDetails.getUsername()).claim("roles", roles).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
				.signWith(key, SignatureAlgorithm.HS256).compact();
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		String username = extractUsername(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	public String extractUsername(String token) {
		return getClaims(token).getSubject();
	}

	/*
	 * public List<GrantedAuthority> extractAuthorities(String token) { Claims
	 * claims = getClaims(token); List<String> roles = claims.get("roles",
	 * List.class); return roles.stream().map(r -> (GrantedAuthority) () ->
	 * r).collect(Collectors.toList()); }
	 */

	public List<GrantedAuthority> extractAuthorities(String token) {
		Claims claims = getClaims(token);
		List<String> roles = claims.get("roles", List.class);

		// Convert string roles like "ROLE_USER" to proper GrantedAuthority objects
		return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	private boolean isTokenExpired(String token) {
		return getClaims(token).getExpiration().before(new Date());
	}

	private Claims getClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}
}
