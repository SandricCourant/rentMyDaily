package com.example.demo.it;

import com.example.demo.domain.Owner;
import com.example.demo.domain.Role;
import com.example.demo.repositories.OwnerRepository;
import com.example.demo.repositories.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(authorities = "ROLE_ADMIN")
public class RoleIntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    OwnerRepository mockOwnerRepository;
    @MockBean
    RoleRepository mockRoleRepository;

    @Test
    public void testGetRoleList() throws Exception {
        //Defining the mock with Mockito
        Role role = new Role();
        role.setId(1);
        role.setName("ROLE_QUEEN");
        Collection<Role> roles = new ArrayList<>();
        roles.add(role);
        Mockito.when(mockRoleRepository.findAll()).thenReturn(roles);
        //Testing
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/roles")).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertTrue(responseBody.contains("\"name\":\"ROLE_QUEEN\""));
    }

    @Test
    public void testCreateRole() throws Exception {
        //Defining the mock with Mockito
        Role role = new Role();
        role.setId(1);
        role.setName("ROLE_QUEEN");
        Mockito.when(mockRoleRepository.save(ArgumentMatchers.any(Role.class))).thenReturn(role);
        //Testing
        String requestBody = "{\"name\":\"ROLE_QUEEN\"}";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/roles").contentType(MediaType.APPLICATION_JSON).content(requestBody)).andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertTrue(responseBody.contains("\"name\":\"ROLE_QUEEN\""));
    }
    @Test
    public void testCreateRoleAlreadyExist() throws Exception {
        //Defining the mock with Mockito
        Role role = new Role();
        role.setId(1);
        role.setName("ROLE_QUEEN");
        Mockito.when(mockRoleRepository.findByName(ArgumentMatchers.anyString())).thenReturn(Optional.of(role));
        //Testing
        String requestBody = "{\"name\":\"ROLE_QUEEN\"}";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/roles").contentType(MediaType.APPLICATION_JSON).content(requestBody)).andExpect(MockMvcResultMatchers.status().isConflict()).andReturn();
        String responseBody = mvcResult.getResponse().getErrorMessage();
        assert responseBody != null;
        Assertions.assertTrue(responseBody.contains("Role already exists"));
    }

    @Test
    public void testGetRole() throws Exception {
        //Defining the mock with Mockito
        Role role = new Role();
        role.setId(1);
        role.setName("ROLE_QUEEN");
        Mockito.when(mockRoleRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(role));
        //Testing
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/roles/9")).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertTrue(responseBody.contains("\"name\":\"ROLE_QUEEN\""));
    }
    @Test
    public void testGetRoleNotFound() throws Exception {
        //Defining the mock with Mockito
        Mockito.when(mockRoleRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        //Testing
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/roles/9")).andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
        String responseBody = mvcResult.getResponse().getErrorMessage();
        assert responseBody != null;
        Assertions.assertTrue(responseBody.contains("Role not found"));
    }

    @Test
    public void testAttachRoleToUser() throws Exception {
        //Defining the mock with Mockito
        Role role = new Role();
        role.setId(1);
        role.setName("ROLE_QUEEN");
        Collection<Role> roles = new ArrayList<>();
        Mockito.when(mockRoleRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(role));
        Owner owner = new Owner();
        owner.setId(1);
        owner.setLogin("UserTest");
        owner.setRoles(roles);
        Mockito.when(mockOwnerRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(owner));
        //Testing
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/roles/9/users/9/attach"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertTrue(responseBody.contains("\"login\":\"UserTest\"") && responseBody.contains("\"name\":\"ROLE_QUEEN\""));
    }
    @Test
    public void testAttachRoleToUserNotFound() throws Exception {
        //Defining the mock with Mockito
        Role role = new Role();
        role.setId(1);
        role.setName("ROLE_QUEEN");
        Mockito.when(mockOwnerRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        Mockito.when(mockRoleRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(role));
        //Testing
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/roles/9/users/9/attach"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
        String responseBody = mvcResult.getResponse().getErrorMessage();
        assert responseBody != null;
        Assertions.assertTrue(responseBody.contains("User not found"));
    }
    @Test
    public void testAttachRoleNotFoundToUser() throws Exception {
        //Defining the mock with Mockito
        Owner owner = new Owner();
        Collection<Role> roles = new ArrayList<>();//Roles already attached to user
        owner.setId(1);
        owner.setLogin("UserTest");
        owner.setRoles(roles);
        Mockito.when(mockOwnerRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(owner));
        Mockito.when(mockRoleRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        //Testing
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/roles/9/users/9/attach"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
        String responseBody = mvcResult.getResponse().getErrorMessage();
        assert responseBody != null;
        Assertions.assertTrue(responseBody.contains("Role not found"));
    }

    @Test
    public void testDetachRoleToUser() throws Exception {
        //Defining the mock with Mockito
        Role role = new Role();
        role.setId(1);
        role.setName("ROLE_QUEEN");
        Collection<Role> roles = new ArrayList<>();
        roles.add(role);
        Mockito.when(mockRoleRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(role));
        Owner owner = new Owner();
        owner.setId(1);
        owner.setLogin("UserTest");
        owner.setRoles(roles);
        Mockito.when(mockOwnerRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(owner));
        //Testing
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/roles/9/users/9/detach")).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertTrue(responseBody.contains("\"login\":\"UserTest\"") && responseBody.contains("\"roles\":[]"));
    }
    @Test
    public void testDetachRoleToUserNotFound() throws Exception {
        //Defining the mock with Mockito
        Role role = new Role();
        role.setId(1);
        role.setName("ROLE_QUEEN");
        Mockito.when(mockOwnerRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        Mockito.when(mockRoleRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(role));
        //Testing
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/roles/9/users/9/detach"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
        String responseBody = mvcResult.getResponse().getErrorMessage();
        assert responseBody != null;
        Assertions.assertTrue(responseBody.contains("User not found"));
    }
    @Test
    public void testDetachRoleNotFoundToUser() throws Exception {
        //Defining the mock with Mockito
        Owner owner = new Owner();
        Collection<Role> roles = new ArrayList<>();//Roles already attached to user
        owner.setId(1);
        owner.setLogin("UserTest");
        owner.setRoles(roles);
        Mockito.when(mockOwnerRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(owner));
        Mockito.when(mockRoleRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        //Testing
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/roles/9/users/9/detach"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
        String responseBody = mvcResult.getResponse().getErrorMessage();
        assert responseBody != null;
        Assertions.assertTrue(responseBody.contains("Role not found"));
    }
    @Test
    public void testDeleteRole() throws Exception {
        //Defining the mock with Mockito
        Mockito.when(mockRoleRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(new Role()));
        //Testing
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/roles/9")).andExpect(MockMvcResultMatchers.status().isNoContent()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertTrue(responseBody.isEmpty());
    }
    @Test
    public void testDeleteRoleNotFound() throws Exception {
        //Defining the mock with Mockito
        Mockito.when(mockRoleRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        //Testing
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/roles/9")).andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
        String responseBody = mvcResult.getResponse().getErrorMessage();
        assert responseBody != null;
        Assertions.assertTrue(responseBody.contains("Role not found"));
    }
}
