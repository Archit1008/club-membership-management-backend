package com.hcl.user.controller;

import com.hcl.user.entity.User;
import com.hcl.user.serviceImpl.AdminService;
import com.hcl.user.serviceImpl.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/user")
public class AdminController {
    @Autowired
    AdminService adminService;
    @GetMapping("/getAllUser")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<User>> getAllUser(){
        List<User>user=adminService.getUser();
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);

    }
    @DeleteMapping("/deleteUser/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String>deleteUser(@PathVariable Long id){
        String result=adminService.deleteUser(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
