package com.example.poc.oauth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
 
@RestController
@CrossOrigin(origins = "*")
public class RestResource 
{
    @GetMapping("/api/users/me")
    public ResponseEntity<UserProfile> profile(OAuth2Authentication auth, @RequestHeader (name="Authorization") String token) 
    {
        //Build some dummy data to return for testing
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = user.getUsername() + "@howtodoinjava.com";
 
        UserProfile profile = new UserProfile();
        profile.setName(user.getUsername());
        profile.setEmail(email);
 
        return ResponseEntity.ok(profile);
    }
}