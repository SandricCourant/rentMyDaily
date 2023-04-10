package com.example.demo.controllers;

import com.example.demo.dto.AuthRequestDto;
import com.example.demo.dto.AuthResponseDto;
import com.example.demo.exceptions.AccountExistsException;
import com.example.demo.services.JwtUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class SecurityController {

    @Autowired
    private JwtUserService jwtUserService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody AuthRequestDto dto) throws AccountExistsException {
        UserDetails user = jwtUserService.save(dto.getUsername(), dto.getPassword());
        String jwt = jwtUserService.generateJwtForUser(user);

        AuthResponseDto response = new AuthResponseDto();
        response.setToken(jwt);
        response.setUser(user);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/authorize")
    public ResponseEntity<AuthResponseDto> authorize(@RequestBody AuthRequestDto dto) throws Exception {
        Authentication authentication = jwtUserService.authenticate(dto.getUsername(), dto.getPassword());

        UserDetails user = (UserDetails) authentication.getPrincipal();
        String jwt = jwtUserService.generateJwtForUser(user);

        AuthResponseDto response = new AuthResponseDto();
        response.setToken(jwt);
        response.setUser(user);

        return ResponseEntity.ok(response);
    }

}
