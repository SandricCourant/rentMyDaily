package com.example.demo.ut;

import com.example.demo.domain.Role;
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

@SpringBootTest
@AutoConfigureMockMvc
public class RoleServiceTest {
    @Autowired
    RoleService roleService;
    @MockBean
    RoleRepository mockRoleRepository;

    @Test
    public void testCreateRole(){
        //Define the Mockito
        Role role = new Role();
        role.setId(1);
        role.setName("QUEEN");

        Mockito.when(mockRoleRepository.save(ArgumentMatchers.any(Role.class))).thenReturn(role);

        //Assertions
        Role result = roleService.create("KING");
        Assertions.assertEquals("QUEEN", result.getName());
    }
    @Test
    public void testGetListRole(){
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
}
