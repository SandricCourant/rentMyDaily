package com.example.demo.dto;

import org.springframework.security.core.userdetails.UserDetails;

public class AuthResponseDto {

    private String token;
    private UserDetails user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserDetails getUser() {
        return user;
    }

    public void setUser(UserDetails user) {
        this.user = user;
    }
}