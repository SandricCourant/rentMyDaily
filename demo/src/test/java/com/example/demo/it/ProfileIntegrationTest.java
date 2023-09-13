package com.example.demo.it;

import com.example.demo.domain.Owner;
import com.example.demo.security.SecurityFilter;
import com.example.demo.services.JwtUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class ProfileIntegrationTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    JwtUserService mockJwtUserService;


    @Test
    public void testGetInfoOfUserConnected() throws Exception {
        //Defining the mock with Mockito
        Owner owner = new Owner();
        owner.setId(1);
        owner.setLogin("WaffleMan");
        Mockito.when(mockJwtUserService.getUserFromJwt(ArgumentMatchers.anyString())).thenReturn(owner);
        //Testing
        String requestHeaderContent = "Bearer JwtTokenUseless";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/me").header("Authorization", requestHeaderContent)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String responseBody = result.getResponse().getContentAsString();
        Assertions.assertTrue(responseBody.contains("\"login\":\"WaffleMan\""));
    }
}
