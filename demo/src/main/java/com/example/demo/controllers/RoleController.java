package com.example.demo.controllers;

import com.example.demo.domain.Role;
import com.example.demo.dto.RoleDto;
import com.example.demo.exceptions.RoleExistsException;
import com.example.demo.exceptions.RoleNotFoundException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.services.RoleService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/v1")
public class RoleController {
    //TODO  INSERT INTO role(name) VALUES('ROLE_ADMIN'); INSERT INTO owner_roles(owner_id, roles_id) VALUES(idAdmin, idRole_Admin); BEFORE use it
    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/roles")
    public ResponseEntity<Iterable<Role>> list() {
        return ResponseEntity.ok(roleService.list()); // Sending a 200 HTTP status code
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/roles")
    public ResponseEntity<Role> create(@RequestBody RoleDto dto) throws URISyntaxException, RoleExistsException {
        Role role = roleService.create(dto.getName());
        URI uri = new URI("/roles/" + role.getId());
        return ResponseEntity.created(uri).body(role);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/roles/{id}")
    public ResponseEntity<Role> get(@PathVariable int id) throws RoleNotFoundException {
        Role role = roleService.get(id);

        return ResponseEntity.ok(role);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/roles/{roleId}/users/{userId}/attach")
    public ResponseEntity<UserDetails> attach(@PathVariable int roleId, @PathVariable int userId) throws URISyntaxException, UserNotFoundException, RoleNotFoundException {
        UserDetails user = userService.get(userId);
        if (user == null) {
            throw new UserNotFoundException();
        }
        roleService.attach(user, roleId);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/roles/{roleId}/users/{userId}/detach")
    public ResponseEntity<UserDetails> detach(@PathVariable int roleId, @PathVariable int userId) throws URISyntaxException, UserNotFoundException, RoleNotFoundException {
        UserDetails user = userService.get(userId);
        if (user == null) {
            throw new UserNotFoundException();
        }
        roleService.detach(user, roleId);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/roles/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) throws RoleNotFoundException {
        roleService.remove(id);
        return ResponseEntity.noContent().build();
    }

}
