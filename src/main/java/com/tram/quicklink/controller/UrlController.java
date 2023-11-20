/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tram.quicklink.controller;

import com.tram.quicklink.model.Url;
import com.tram.quicklink.model.User;
import com.tram.quicklink.model.UserHelper;
import com.tram.quicklink.service.UrlService;
import com.tram.quicklink.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UrlController {

    @Autowired
    private UrlService urlService;
    
    @Autowired
    private UserHelper userHelper;
;
    /**
     * 处理缩短URL的请求
     *
     * @param originalUrl 用户提交的原始URL
     * @return 缩短后的URL
     */
    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(@RequestBody String originalUrl, HttpServletRequest request) {
        User currentUser = userHelper.getCurrentUserFromRequest(request);
        Url url = urlService.shortenUrl(originalUrl, currentUser);
        if (url == null) {
            return ResponseEntity.badRequest().body("Invalid URL");
        }
        return ResponseEntity.ok(url.getShortUrl());
    }

    /**
     * 重定向到原始URL
     *
     * @param shortUrl 缩短后的URL
     * @return 重定向响应
     */
    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirectToOriginalUrl(@PathVariable String shortUrl, HttpServletRequest request) {
        User currentUser = userHelper.getCurrentUserFromRequest(request);
        String originalUrl = urlService.getOriginalUrl(shortUrl);
        
        if (originalUrl == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }

    @GetMapping("/user/{userId}/urls")
    public ResponseEntity<List<Url>> getUserUrls(@PathVariable Long userId) {
        List<Url> urls = urlService.getUrlsByUser(userId);
        return ResponseEntity.ok(urls);
    }

    // 如果需要，可以添加其他方法，如获取用户的URL历史等
}
