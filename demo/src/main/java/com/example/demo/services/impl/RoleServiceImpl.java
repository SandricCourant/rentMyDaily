package com.example.demo.services.impl;

import com.example.demo.domain.Role;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role create(String name) {
        Role role = new Role();
        role.setName(name);
        return roleRepository.save(role);
    }

    @Override
    public void remove(int id) {
        roleRepository.deleteById(id);
    }

    @Override
    public void attach(UserDetails user, int id) {
        roleRepository.findById(id).ifPresent(role -> ((Collection<GrantedAuthority>) user.getAuthorities()).add(role));
    }

    @Override
    public void detach(UserDetails user, int id) {
        GrantedAuthority role = roleRepository.findById(id).orElse(null);
        ((Collection<GrantedAuthority>) user.getAuthorities()).remove(role);
    }

    @Override
    public Iterable<Role> list() {
        return roleRepository.findAll();
    }

    @Override
    public Role get(int id) {
        return roleRepository.findById(id).orElse(null);
    }
}
