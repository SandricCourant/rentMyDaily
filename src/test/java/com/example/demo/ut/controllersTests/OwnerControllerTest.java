package com.example.demo.ut.controllersTests;

import com.example.demo.controllers.OwnerController;
import com.example.demo.domain.Owner;
import com.example.demo.services.JwtOwnerService;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OwnerControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private OwnerController ownerController;
    @MockBean
    private JwtOwnerService mockJwtOwnerService;
    @Test
    public void testRegister() throws Exception {
        Owner owner = new Owner();
        owner.setId(1);
        owner.setLogin("jc");
        Mockito.when(
                mockJwtOwnerService.save(ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString())
        ).thenReturn(owner);

        String requestBody = "{" +
                "\"username\":\"jc\"," +
                "\"firstname\":\"jean\"," +
                "\"lastname\":\"claude\"," +
                "\"email\":\"jc@gmail.com\"," +
                "\"password\":\"mdp\"," +
                "\"phoneNumber\":\"0606060606\"" +
                "}";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/rmdaily/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andReturn();
        String responseBody = result.getResponse().getContentAsString();
        Assertions.assertTrue(responseBody.contains("\"username\":\"jc\""));
    }
    @Test
    public void testRegisterUserNotFound() throws Exception{
        Mockito.when(
                mockJwtOwnerService.save(ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString())
        ).thenReturn(null);
        String requestBody = "{" +
                "\"username\":\"jc\"," +
                "\"firstname\":\"jean\"," +
                "\"lastname\":\"claude\"," +
                "\"email\":\"jc@gmail.com\"," +
                "\"password\":\"mdp\"," +
                "\"phoneNumber\":\"0606060606\"" +
                "}";
        mvc.perform(MockMvcRequestBuilders.post("/rmdaily/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isConflict())
                .andReturn();
    }
    @Test
    public void testGetUser() throws Exception{
        Owner owner = new Owner();
        owner.setId(1);
        Mockito.when(
                mockJwtOwnerService.findById(ArgumentMatchers.anyInt())
        ).thenReturn(owner);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/rmdaily/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String responseBody = result.getResponse().getContentAsString();
        Assertions.assertTrue(responseBody.contains("\"id\":1"));
    }
    @Test
    public void testGetUserNotFound() throws Exception{
        Mockito.when(
                mockJwtOwnerService.findById(ArgumentMatchers.anyInt())
        ).thenReturn(null);
        mvc.perform(MockMvcRequestBuilders.get("/rmdaily/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }
    @Test
    public void testGetUserItem() throws Exception{
        Owner owner = new Owner();
        owner.setId(1);
        Mockito.when(
                mockJwtOwnerService.findById(ArgumentMatchers.anyInt())
        ).thenReturn(owner);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/rmdaily/v1/users/1/items")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String responseBody = result.getResponse().getContentAsString();
        Assertions.assertTrue(responseBody.isEmpty());
    }
    @Test
    public void testGetUserItemWithoutUser() throws Exception{
        Mockito.when(
                mockJwtOwnerService.findById(ArgumentMatchers.anyInt())
        ).thenReturn(null);
        mvc.perform(MockMvcRequestBuilders.get("/rmdaily/v1/users/1/items")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }


}
