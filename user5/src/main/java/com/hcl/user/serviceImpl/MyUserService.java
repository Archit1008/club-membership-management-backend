package com.hcl.user.serviceImpl;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hcl.user.repository.UserRepository;
import com.hcl.user.serviceInterface.UserInterface;


@Service
public class MyUserService implements UserInterface,UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
           com.hcl.user.entity.User appUser = userRepo.findByUsername(name)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + name));

        return User.builder()
            .username(appUser.getUsername())
            .password(appUser.getPassword())
            .authorities(appUser.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList()))
            .build();
    }
}