package com.bloodnetwork.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bloodnetwork.entity.User;
import com.bloodnetwork.entity.enums.RoleType;
import com.bloodnetwork.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User register(User user) {
        if (user.getPassword() != null && !user.getPassword().startsWith("$2a$")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if (user.getRole() == null) {
            user.setRole(RoleType.ROLE_DONOR);
        }
        if (user.getFullName() == null && user.getName() != null) {
            user.setFullName(user.getName());
        }
        return repository.save(user);
    }
}