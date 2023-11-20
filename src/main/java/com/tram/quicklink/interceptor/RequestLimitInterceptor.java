/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tram.quicklink.interceptor;

import com.tram.quicklink.model.User;
import com.tram.quicklink.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RequestLimitInterceptor implements HandlerInterceptor {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String username = request.getHeader("Username");
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            User currentUser = user.get();
            int tierLevel = currentUser.getTierLevel();
            int requestCount = currentUser.getRequestCount();

            // 设置不同的限额
            int limit = tierLevel == 1 ? 1000 : 100;

            if (requestCount >= limit) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Request limit exceeded");
                return false;
            }

            // 更新请求次数
            currentUser.setRequestCount(requestCount + 1);
            userRepository.save(currentUser);
        }

        return true;
    }

    // 其他必要的方法 ...
}

