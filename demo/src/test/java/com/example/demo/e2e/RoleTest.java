package com.example.demo.e2e;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithMockUser(authorities = "ROLE_ADMIN")
public class RoleTest {
    @Autowired
    MockMvc mockMvc;
    @Test
    public void getRoleList() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/roles")).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertTrue(responseBody.contains("[]"));
    }
    @Test
    public void testCreateRole() throws Exception {
        String requestBody = "{\"name\":\"ROLE_QUEEN\"}";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/roles").contentType(MediaType.APPLICATION_JSON).content(requestBody)).andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        Assertions.assertTrue(responseBody.contains("\"name\":\"ROLE_QUEEN\""));
    }
    @Test
    public void testGetRoleNotFound() throws Exception {
        //Testing
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/roles/9")).andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
        String responseBody = mvcResult.getResponse().getErrorMessage();
        assert responseBody != null;
        Assertions.assertTrue(responseBody.contains("Role not found"));
    }
    @Test
    public void testAttachRoleToUserNotFound() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/roles/9/users/9/attach"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
        String responseBody = mvcResult.getResponse().getErrorMessage();
        assert responseBody != null;
        Assertions.assertTrue(responseBody.contains("User not found"));
    }
    @Test
    public void testDetachRoleToUserNotFound() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/roles/9/users/9/detach"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
        String responseBody = mvcResult.getResponse().getErrorMessage();
        assert responseBody != null;
        Assertions.assertTrue(responseBody.contains("User not found"));
    }
    @Test
    public void testDeleteRoleNotFound() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/roles/9")).andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
        String responseBody = mvcResult.getResponse().getErrorMessage();
        assert responseBody != null;
        Assertions.assertTrue(responseBody.contains("Role not found"));
    }
}
