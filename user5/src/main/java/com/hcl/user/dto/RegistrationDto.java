package com.hcl.user.dto;


import java.util.Set;

import com.hcl.user.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDto {
  public String username;
  public String password;
  public String email;
  public Set<Role>roles;
  }
