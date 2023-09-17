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
public class ItemTest {
    @Autowired
    MockMvc mockMvc;
    @Test
    public void testGetAllItems() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/items/view"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        String responseBody = result.getResponse().getContentAsString();
        Assertions.assertTrue(responseBody.contains("[]"));
    }
    @Test
    @WithMockUser()
    public void testCreateItem() throws Exception {
        String requestBody = "{\"name\":\"waffle\",\"description\":\"a waffle maker to rent\"}";
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/items/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();
        String responseBody = result.getResponse().getContentAsString();
        Assertions.assertTrue(responseBody.contains("\"name\":\"waffle\""));
    }
    @Test
    @WithMockUser()
    public void testRemoveItemNotFound() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/items/9/remove")).andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
        String responseError = result.getResponse().getErrorMessage();
        assert responseError != null;
        Assertions.assertTrue(responseError.contains("Item not found"));
    }
}
