package com.example.demo.controllers;

import com.example.demo.domain.Owner;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin()
@RestController
@RequestMapping("/api/v1/")
public class ProfileController {

    @GetMapping("me")
    public ResponseEntity<UserDetails> me(@AuthenticationPrincipal Owner user) {
        return ResponseEntity.ok(user); // Sending a 200 HTTP status code
    }

}
