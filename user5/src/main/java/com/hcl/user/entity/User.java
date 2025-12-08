package com.hcl.user.entity;


import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="UserInfo")
@Entity
public class User {
   @Id
   @GeneratedValue(strategy=GenerationType.IDENTITY)
   private Long id;
   private String email;
   private String username;
   
   private String password;       
   @ElementCollection(fetch=FetchType.EAGER)   
   @Enumerated(EnumType.STRING)
   private Set<Role>roles=new HashSet<>();
}
