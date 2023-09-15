package com.example.demo.e2e;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class SecurityTest {
    @Autowired
    MockMvc mockMvc;
    @Test
    public void testRegister() throws Exception {
        String requestBody = "{" +
                "\"username\":\"QueenUser\"," +
                "\"password\":\"HelloWorld\"," +
                "\"firstname\":\"Jean\"," +
                "\"lastname\":\"Michel\"," +
                "\"email\":\"jean.michel@gmail.com\"," +
                "\"phoneNumber\":\"0607080901\"" +
                "}";
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/account/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseBody = result.getResponse().getContentAsString();
        Assertions.assertTrue(responseBody.contains("\"token\":") && responseBody.contains("\"login\":\"QueenUser\""));
    }
}
