package com.tram.quicklink.model;

import com.tram.quicklink.model.User;
import com.tram.quicklink.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
public class UserHelper {

    @Autowired
    private UserRepository userRepository;

    public User getCurrentUserFromRequest(HttpServletRequest request) {
    String username = request.getHeader("Username");
    Optional<User> user = userRepository.findByUsername(username);

    if (user.isPresent()) {
        return user.get();
    } else {
        // 处理未找到用户的情况
        throw new RuntimeException("User not found");
    }
}
}

