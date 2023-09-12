package com.example.demo.ut;

import com.example.demo.controllers.RoleController;
import com.example.demo.domain.Owner;
import com.example.demo.domain.Role;
import com.example.demo.dto.RoleDto;
import com.example.demo.exceptions.RoleExistsException;
import com.example.demo.exceptions.RoleNotFoundException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.services.JwtUserService;
import com.example.demo.services.RoleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(authorities = {"ROLE_ADMIN"})
public class RoleControllerTest {
    @Autowired
    RoleController roleController;
    @MockBean
    RoleService mockRoleService;
    @MockBean
    JwtUserService mockUserService;

    @Test
    public void testGetList() {
        //Defining the mock with Mockito
        List<Role> roles = new ArrayList<>();

        Mockito.when(mockRoleService.list()).thenReturn(roles);

        //Testing
        Iterable<Role> results = roleController.list().getBody();
        Assertions.assertIterableEquals(roles, results);
    }

    @Test
    public void testCreateRole() throws Exception {
        //Defining the mock with Mockito
        RoleDto dto = new RoleDto();
        dto.setName("QUEEN");
        Role role = new Role();
        role.setId(1);
        role.setName("QUEEN");

        Mockito.when(mockRoleService.create(ArgumentMatchers.anyString())).thenReturn(role);

        //Testing
        Role result = roleController.create(dto).getBody();
        assert result != null;
        Assertions.assertEquals("QUEEN", result.getName());
    }

    @Test
    public void testCreateRoleMakeURI() throws Exception {
        //Defining the mock with Mockito
        RoleDto dto = new RoleDto();
        dto.setName("QUEEN");
        Role role = new Role();
        role.setId(1);
        role.setName("QUEEN");

        Mockito.when(mockRoleService.create(ArgumentMatchers.anyString())).thenReturn(role);

        //Testing
        URI result = roleController.create(dto).getHeaders().getLocation();
        assert result != null;
        Assertions.assertEquals("/roles/1", result.toString());
    }

    @Test
    public void testCreateRoleAlreadyExist() {
        //Defining the mock with Mockito
        RoleDto dto = new RoleDto();
        dto.setName("QUEEN");

        Mockito.when(mockRoleService.create(ArgumentMatchers.anyString())).thenThrow(RoleExistsException.class);

        //Testing
        Assertions.assertThrows(RoleExistsException.class, () -> {
            roleController.create(dto);
        });
    }

    @Test
    public void testGetRole() {
        //Defining the mock with Mockito
        Role role = new Role();
        role.setId(1);
        role.setName("QUEEN");

        Mockito.when(mockRoleService.get(ArgumentMatchers.anyInt())).thenReturn(role);

        //Testing
        Role result = roleController.get(9).getBody();
        assert result != null;
        Assertions.assertEquals("QUEEN", result.getName());
    }

    @Test
    public void testGetRoleNotFound() {
        //Defining the mock with Mockito
        Mockito.when(mockRoleService.get(ArgumentMatchers.anyInt())).thenThrow(RoleNotFoundException.class);

        //Testing
        Assertions.assertThrows(RoleNotFoundException.class, () -> {
            roleController.get(9);
        });
    }

    @Test
    public void testAttachRoleToUser() throws UserNotFoundException {
        //Defining the mock with Mockito
        Owner user = new Owner();
        user.setId(1);
        user.setLogin("UserQueen");

        Mockito.when(mockUserService.get(ArgumentMatchers.anyInt())).thenReturn(user);

        //Testing
        UserDetails result = roleController.attach(9, 9).getBody();
        assert result != null;
        Assertions.assertEquals("UserQueen", result.getUsername());
    }

    @Test
    public void testAttachRoleToUserNotFound() {
        //Defining the mock with Mockito
        Mockito.when(mockUserService.get(ArgumentMatchers.anyInt())).thenReturn(null);

        //Testing
        Assertions.assertThrows(UserNotFoundException.class, () -> {
            roleController.attach(9, 9);
        });
    }

    @Test
    public void testAttachRoleNotFoundToUser() {
        //Defining the mock with Mockito
        Owner user = new Owner();
        user.setId(1);
        user.setLogin("UserQueen");

        Mockito.when(mockUserService.get(ArgumentMatchers.anyInt())).thenReturn(user);
        Mockito.doThrow(new RoleNotFoundException()).when(mockRoleService).attach(ArgumentMatchers.any(UserDetails.class), ArgumentMatchers.anyInt());

        //Testing
        Assertions.assertThrows(RoleNotFoundException.class, () -> {
            roleController.attach(9, 9);
        });
    }

    @Test
    public void testDetachRoleToUser() throws UserNotFoundException {
        //Defining the mock with Mockito
        Owner user = new Owner();
        user.setId(1);
        user.setLogin("UserQueen");

        Mockito.when(mockUserService.get(ArgumentMatchers.anyInt())).thenReturn(user);

        //Testing
        UserDetails result = roleController.detach(9, 9).getBody();
        assert result != null;
        Assertions.assertEquals("UserQueen", result.getUsername());
    }

    @Test
    public void testDetachRoleToUserNotFound() {
        //Defining the mock with Mockito
        Mockito.when(mockUserService.get(ArgumentMatchers.anyInt())).thenReturn(null);

        //Testing
        Assertions.assertThrows(UserNotFoundException.class, () -> {
            roleController.detach(9, 9);
        });
    }

    @Test
    public void testDetachRoleNotFoundToUser() {
        //Defining the mock with Mockito
        Owner user = new Owner();
        user.setId(1);
        user.setLogin("UserQueen");

        Mockito.when(mockUserService.get(ArgumentMatchers.anyInt())).thenReturn(user);
        Mockito.doThrow(new RoleNotFoundException()).when(mockRoleService).detach(ArgumentMatchers.any(UserDetails.class), ArgumentMatchers.anyInt());

        //Testing
        Assertions.assertThrows(RoleNotFoundException.class, () -> {
            roleController.detach(9, 9);
        });
    }
}
