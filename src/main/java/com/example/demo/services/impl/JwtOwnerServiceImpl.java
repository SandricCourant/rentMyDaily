package com.example.demo.services.impl;

import com.example.demo.domain.Owner;
import com.example.demo.exceptions.AccountExistsException;
import com.example.demo.repositories.OwnerRepository;
import com.example.demo.services.JwtOwnerService;
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
public class JwtOwnerServiceImpl implements JwtOwnerService {
    private final String signingKey;

    public JwtOwnerServiceImpl() {
        this.signingKey = "+ze]8D[Z-AcvVc446Lt,G65}b8";
    }
    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private OwnerRepository ownerRepository;

    @Override
    public Authentication authenticate(String username, String password) throws Exception {
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
        authentication = authenticationConfiguration.getAuthenticationManager().authenticate(authentication);
        return authentication;
    }

    @Override
    public UserDetails save(String username, String password,  String firstname, String lastname, String email, String phoneNumber) throws AccountExistsException {
        UserDetails user = ownerRepository.findByLogin(username).orElse(null);
        if (user != null) {
            throw new AccountExistsException();
        }

        Owner owner = new Owner();
        owner.setEmail(email);
        owner.setLogin(username);
        owner.setFirstname(firstname);
        owner.setLastname(lastname);
        owner.setPassword(passwordEncoder.encode(password));
        owner.setPhoneNumber(phoneNumber);
        return ownerRepository.save(owner);
    }

    @Override
    public UserDetails get(int id) {
        return null;
    }

    @Override
    public Owner findById(int id){
        return ownerRepository.findById(id).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = ownerRepository.findByLogin(username).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("The user cannot be found");
        }
        return user;
    }

    @Override
    public String generateJwtForUser(UserDetails user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 3600 * 1000);
        String username = user.getUsername();

        String jwt = Jwts.builder().
                setSubject(username).setIssuedAt(now).setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, signingKey).compact();

        return jwt;
    }

    @Override
    public UserDetails getUserFromJwt(String jwt) {
        Claims claims = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(jwt).getBody();
        String username = claims.getSubject();
        UserDetails user = loadUserByUsername(username);
        return user;
    }

}
