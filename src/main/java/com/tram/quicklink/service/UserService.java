/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tram.quicklink.service;

import com.tram.quicklink.model.User;
import com.tram.quicklink.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean validateUser(String username, String password) {
        Optional<User> user = userRepository.findByUsernameAndPassword(username, password);
        return user.isPresent();
    }

    // 其他方法...
}

