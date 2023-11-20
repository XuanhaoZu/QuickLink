package com.tram.quicklink.controller;

import com.tram.quicklink.model.User;
import com.tram.quicklink.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService; // 假设您有一个处理用户相关逻辑的服务

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User loginUser) {
        boolean isValidUser = userService.validateUser(loginUser.getUsername(), loginUser.getPassword());
        if (isValidUser) {
            // 登录成功的逻辑
            return ResponseEntity.ok("Login successful");
        } else {
            // 登录失败的逻辑
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
}


