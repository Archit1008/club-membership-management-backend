package com.hcl.user.serviceImpl;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.BeanDefinitionDsl.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hcl.user.dto.RegistrationDto;
import com.hcl.user.entity.User;
import com.hcl.user.repository.UserRepository;
import com.hcl.user.securityconfig.JwtUtil;

@Service
public class AuthService {
      @Autowired
      private  UserRepository userRepo;
      @Autowired
      private PasswordEncoder passwordEncoder;
      @Autowired
      private JwtUtil jwtService;
      public String Register(RegistrationDto request)
       {
    	   User user=new User();
    	   user.setUsername(request.getUsername());
    	   user.setPassword(passwordEncoder.encode(request.getPassword()));
//    	   Set<Role>roleSet=request.getRoles().stream().map(role->Role.valueOf(role.toUpperCase())).collect(Collectors.toSet());
    	   user.setRoles(request.getRoles());
    	   user.setEmail(request.getEmail());
    	   userRepo.save(user);
    	   return "Successfully save user data";
       }


      
}
