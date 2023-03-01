package com.example.demo.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtOwnerService extends OwnerService {
    String generateJwtForUser(UserDetails user);

    UserDetails getUserFromJwt(String jwt);
}
