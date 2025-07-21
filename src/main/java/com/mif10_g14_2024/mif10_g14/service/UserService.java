package com.mif10_g14_2024.mif10_g14.service;

import com.mif10_g14_2024.mif10_g14.dao.UserRepository;
import com.mif10_g14_2024.mif10_g14.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    public Optional<User> getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }
}