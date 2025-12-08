package com.hcl.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.user.dto.AuthRequestDto;
import com.hcl.user.dto.RegistrationDto;
import com.hcl.user.entity.User;
import com.hcl.user.securityconfig.JwtUtil;
import com.hcl.user.serviceImpl.AuthService;
import com.hcl.user.serviceImpl.MyUserService;

import lombok.var;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private final MyUserService myUserService;
	private final AuthService authService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
	public AuthController(PasswordEncoder passwordEncoder,AuthenticationManager authenticationManager,JwtUtil jwtUtil,AuthService authService,MyUserService myUserService)
	{
		this.passwordEncoder=passwordEncoder;
		this.authenticationManager=authenticationManager;
		this.jwtUtil=jwtUtil;
		this.authService=authService;
		this.myUserService=myUserService;
	}
	@PostMapping("/registration")
	public ResponseEntity<String>saveUserData(@RequestBody RegistrationDto registraionDto){
		String s=authService.Register(registraionDto);
		return new ResponseEntity<>(s,HttpStatus.ACCEPTED);
	}
	@PostMapping("/login")
	public ResponseEntity<?>loginUserData(@RequestBody AuthRequestDto request){
		try {
			Authentication auth=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username,request.password));
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(401).body("Auth failed: " + ex.getMessage());
		}
		var userDetails=myUserService.loadUserByUsername(request.username);
		String token=jwtUtil.generateToken(userDetails);
		return ResponseEntity.ok(token);		
	}
	

}
