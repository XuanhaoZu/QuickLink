/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tram.quicklink.repository;

import com.tram.quicklink.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    
    // 根据用户名查找用户
    Optional<User> findByUsername(String username);

    // 根据用户名和密码查找用户，用于登录验证等
    Optional<User> findByUsernameAndPassword(String username, String password);

    // 可以根据需要添加其他自定义查询方法
}
