package com.example.demo.services;

import com.example.demo.domain.Role;
import com.example.demo.exceptions.RoleExistsException;
import com.example.demo.exceptions.RoleNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;

public interface RoleService {

    Role create(String name) throws RoleExistsException;

    void remove(int id) throws RoleNotFoundException;

    void attach(UserDetails user, int id) throws RoleNotFoundException;

    void detach(UserDetails user, int id) throws RoleNotFoundException;

    Iterable<Role> list();

    Role get(int id) throws RoleNotFoundException;
}

