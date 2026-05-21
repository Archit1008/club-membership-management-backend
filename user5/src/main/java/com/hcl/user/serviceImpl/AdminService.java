package com.hcl.user.serviceImpl;

import com.hcl.user.entity.User;
import com.hcl.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    private UserRepository userRepo;
    public List<User> getUser(){
        List<User>user=userRepo.findAll();
        return user;
    }
    public String deleteUser(Long id) {
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent()) {
            userRepo.deleteById(id);
            return "User deleted successfully";
        } else {
            return "id not found";
        }
    }
}
