package com.hcl.user.serviceInterface;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


public interface UserInterface {
	UserDetails loadUserByUsername(String name) throws UsernameNotFoundException;
}
