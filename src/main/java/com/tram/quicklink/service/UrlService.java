/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tram.quicklink.service;

import com.tram.quicklink.model.Url;
import com.tram.quicklink.model.User;
import com.tram.quicklink.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    public Url shortenUrl(String originalUrl, User currentUser) {
        String shortUrl = generateShortUrl(originalUrl);
        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortUrl(shortUrl);
        url.setCreatedDate(LocalDateTime.now());
        
        url.setUser(currentUser);
        return urlRepository.save(url);
    }

    public String getOriginalUrl(String shortUrl) {
        Optional<Url> url = urlRepository.findByShortUrl(shortUrl);
        return url.isPresent() ? url.get().getOriginalUrl() : null;
    }

    private String generateShortUrl(String originalUrl) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(originalUrl.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().encodeToString(hash).substring(0, 8);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error creating hash", e);
        }
    }
    
    
    public List<Url> getUrlsByUser(Long userId) {
        return urlRepository.findAllByUser_Id(userId);
    }
}

