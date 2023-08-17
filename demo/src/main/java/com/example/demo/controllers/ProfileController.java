package com.example.demo.controllers;

import com.example.demo.domain.Item;
import com.example.demo.domain.Owner;
import com.example.demo.dto.ItemDto;
import com.example.demo.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
@CrossOrigin()
@RestController
@RequestMapping("/api/v1/")
public class ProfileController {

    @GetMapping("/me")
    public ResponseEntity<UserDetails> me(@AuthenticationPrincipal Owner user) {
        return ResponseEntity.ok(user); // Sending a 200 HTTP status code
    }

}
