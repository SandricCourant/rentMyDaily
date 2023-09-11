package com.example.demo.services.impl;

import com.example.demo.domain.Role;
import com.example.demo.exceptions.RoleExistsException;
import com.example.demo.exceptions.RoleNotFoundException;
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
    public Role create(String name) throws RoleExistsException {
        roleRepository.findByName(name).ifPresent(r -> {
            throw new RoleExistsException();
        });

        Role role = new Role();
        role.setName(name);
        return roleRepository.save(role);
    }

    @Override
    public void remove(int id) throws RoleNotFoundException {
        roleRepository.findById(id).ifPresentOrElse(role -> roleRepository.deleteById(role.getId()), () -> {
            throw new RoleNotFoundException();
        });
    }

    @Override
    public void attach(UserDetails user, int id) throws RoleNotFoundException {
        roleRepository.findById(id).ifPresentOrElse(role -> ((Collection<GrantedAuthority>) user.getAuthorities()).add(role), () -> {
            throw new RoleNotFoundException();
        });
    }

    @Override
    public void detach(UserDetails user, int id) throws RoleNotFoundException {
        roleRepository.findById(id).ifPresentOrElse(role -> user.getAuthorities().remove(role), () -> {
            throw new RoleNotFoundException();
        });
    }

    @Override
    public Iterable<Role> list() {
        return roleRepository.findAll();
    }

    @Override
    public Role get(int id) throws RoleNotFoundException {
        return roleRepository.findById(id).orElseThrow(RoleNotFoundException::new);
    }
}
