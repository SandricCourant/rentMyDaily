package com.example.demo.it;

import com.example.demo.domain.Role;
import com.example.demo.repositories.OwnerRepository;
import com.example.demo.repositories.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collection;

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
    public void testGetRoleList() throws Exception{
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
}
