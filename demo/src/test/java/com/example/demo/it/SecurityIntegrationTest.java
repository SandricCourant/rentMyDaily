package com.example.demo.it;

import com.example.demo.domain.Owner;
import com.example.demo.repositories.OwnerRepository;
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
public class SecurityIntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    OwnerRepository mockOwnerRepository;

    @Test
    public void testRegister() throws Exception {
        //Defining the mock with Mockito
        Owner owner = new Owner();
        owner.setId(1);
        owner.setLogin("QueenUser");
        Mockito.when(mockOwnerRepository.findByLoginOrEmailOrPhoneNumber(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(null);
        Mockito.when(mockOwnerRepository.save(ArgumentMatchers.any(Owner.class))).thenReturn(owner);
        //Testing
        String requestBody = "{" +
                "\"username\":\"JeanMichel\"," +
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
    @Test
    public void testRegisterWithAccountAlreadyExist() throws Exception {
        //Defining the mock with Mockito
        Owner owner = new Owner();
        owner.setId(1);
        owner.setLogin("QueenUser");
        Mockito.when(mockOwnerRepository.findByLoginOrEmailOrPhoneNumber(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(owner);
        //Testing
        String requestBody = "{" +
                "\"username\":\"JeanMichel\"," +
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
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andReturn();
        String responseError = result.getResponse().getErrorMessage();
        assert responseError != null;
        Assertions.assertTrue(responseError.contains("Account already exists"));
    }
}
