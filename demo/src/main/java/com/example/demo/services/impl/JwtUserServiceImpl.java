package com.example.demo.services.impl;

import com.example.demo.domain.Owner;
import com.example.demo.exceptions.AccountExistsException;
import com.example.demo.repositories.OwnerRepository;
import com.example.demo.services.JwtUserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtUserServiceImpl implements JwtUserService {

    private final String signingKey;
    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private OwnerRepository ownerRepository;

    public JwtUserServiceImpl() {
        this.signingKey = "motdepassebien";
    }

    @Override
    public String generateJwtForUser(UserDetails user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 3600 * 1000);
        String username = user.getUsername();

        return Jwts.builder().setSubject(username).setIssuedAt(now).setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, signingKey).compact();
    }

    @Override
    public UserDetails getUserFromJwt(String jwt) {
        Claims claims = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(jwt).getBody();
        String username = claims.getSubject();
        return loadUserByUsername(username);
    }

    @Override
    public Authentication authenticate(String username, String password) throws Exception {
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
        authentication = authenticationConfiguration.getAuthenticationManager().authenticate(authentication);
        return authentication;
    }

    @Override
    public UserDetails save(String username, String password, String firstname, String lastname, String email, String phoneNumber) throws AccountExistsException {
        UserDetails user = ownerRepository.findByLoginOrEmailOrPhoneNumber(username, email, phoneNumber);
        if (user != null) {
            throw new AccountExistsException();
        }

        Owner owner = new Owner();
        owner.setLogin(username);
        owner.setPassword(passwordEncoder.encode(password));
        owner.setFirstname(firstname);
        owner.setLastname(lastname);
        owner.setEmail(email);
        owner.setPhoneNumber(phoneNumber);

        return ownerRepository.save(owner);
    }

    @Override
    public UserDetails get(int id) {
        return ownerRepository.findById(id).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = ownerRepository.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException("The user cannot be found");
        }
        return user;
    }
}
