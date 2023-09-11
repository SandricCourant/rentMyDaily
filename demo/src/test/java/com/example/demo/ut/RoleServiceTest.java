package com.example.demo.ut;

import com.example.demo.domain.Owner;
import com.example.demo.domain.Role;
import com.example.demo.exceptions.RoleExistsException;
import com.example.demo.exceptions.RoleNotFoundException;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.services.RoleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class RoleServiceTest {
    @Autowired
    RoleService roleService;
    @MockBean
    RoleRepository mockRoleRepository;

    @Test
    public void testCreateRole() {
        //Define the Mockito
        Role role = new Role();
        role.setId(1);
        role.setName("QUEEN");

        Mockito.when(mockRoleRepository.findByName(ArgumentMatchers.anyString())).thenReturn(Optional.empty());
        Mockito.when(mockRoleRepository.save(ArgumentMatchers.any(Role.class))).thenReturn(role);

        //Assertions
        Role result = roleService.create("KING");
        Assertions.assertEquals("QUEEN", result.getName());
    }

    @Test
    public void testCreateRoleAlreadyExist() {
        //Define the Mockito
        Role role = new Role();
        role.setId(1);
        role.setName("QUEEN");

        Mockito.when(mockRoleRepository.findByName(ArgumentMatchers.anyString())).thenReturn(Optional.of(role));

        //Assertions
        Assertions.assertThrows(RoleExistsException.class, () -> roleService.create("KING"));
    }

    @Test
    public void testGetListRole() {
        //Define the mockito
        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setName("QUEEN");
        roles.add(role);

        Mockito.when(mockRoleRepository.findAll()).thenReturn(roles);

        //Assertions
        Iterable<Role> results = roleService.list();
        Assertions.assertIterableEquals(roles, results);
    }

    @Test
    public void testGetRole() {
        //Define the Mockito
        Role role = new Role();
        role.setId(1);
        role.setName("QUEEN");

        Mockito.when(mockRoleRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(role));

        //Assertions
        Role result = roleService.get(9);
        Assertions.assertEquals("QUEEN", result.getName());
    }

    @Test
    public void testGetRoleNotFound() {
        //Define the Mockito
        Mockito.when(mockRoleRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());

        //Assertions
        Assertions.assertThrows(RoleNotFoundException.class, () -> roleService.get(9));
    }

    @Test
    public void testRemoveRoleNotFound() {
        //Define the Mockito
        Mockito.when(mockRoleRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        //Assertions
        Assertions.assertThrows(RoleNotFoundException.class, () -> roleService.remove(9));
    }

    @Test
    public void testAttachRoleNotFound() {
        //Define the Mockito
        Mockito.when(mockRoleRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        //Assertions
        Assertions.assertThrows(RoleNotFoundException.class, () -> roleService.attach(new Owner(), 9));
    }

    @Test
    public void testDetachRoleNotFound() {
        //Define the Mockito
        Mockito.when(mockRoleRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        //Assertions
        Assertions.assertThrows(RoleNotFoundException.class, () -> roleService.detach(new Owner(), 9));
    }
}
