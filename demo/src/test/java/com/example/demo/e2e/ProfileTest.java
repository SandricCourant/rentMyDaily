package com.example.demo.e2e;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProfileTest {
    @Autowired
    MockMvc mockMvc;
    @Test
    public void testGetInfoOfUserNotConnected() throws Exception {
        //Testing
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/me")).andExpect(MockMvcResultMatchers.status().is(403)).andReturn();
        String responseBody = result.getResponse().getErrorMessage();
        assert responseBody != null;
        Assertions.assertTrue(responseBody.contains("Access Denied"));
    }
}
