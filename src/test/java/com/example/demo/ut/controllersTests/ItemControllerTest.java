package com.example.demo.ut.controllersTests;

import com.example.demo.controllers.ItemController;
import com.example.demo.domain.Item;
import com.example.demo.domain.Owner;
import com.example.demo.services.ItemService;
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
public class ItemControllerTest {
    @Autowired
    private ItemController itemController;
    @Autowired
    private MockMvc mvc;
    @MockBean
    private ItemService mockItemService;
    @MockBean
    private JwtOwnerService mockJwtOwnerService;
    @Test
    public void testCreateItem() throws Exception {
        Item item = new Item();
        item.setId(1);
        item.setName("raclette");
        Mockito.when(
                mockJwtOwnerService.findById(ArgumentMatchers.anyInt())
        ).thenReturn(new Owner());
        Mockito.when(
                mockItemService.createdItem(ArgumentMatchers.any(Owner.class), ArgumentMatchers.anyString())
        ).thenReturn(item);

        String requestBody = "{\"name\": \"raclette\"}";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/rmdaily/v1/users/1/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andReturn();
        String responseBody = result.getResponse().getContentAsString();
        Assertions.assertTrue(responseBody.contains("raclette"));
    }
    @Test
    public void testCreateItemWithoutUser() throws Exception {
        Mockito.when(
                mockJwtOwnerService.findById(ArgumentMatchers.anyInt())
        ).thenReturn(null);

        String requestBody = "{\"name\": \"raclette\"}";
        mvc.perform(MockMvcRequestBuilders.post("/rmdaily/v1/users/1/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotFound())
                .andReturn();
    }
    @Test
    public void testDeleteWithoutItem() throws Exception{
        Mockito.when(
                mockItemService.remove(ArgumentMatchers.anyInt())
        ).thenReturn(false);

        mvc.perform(MockMvcRequestBuilders.delete("/rmdaily/v1/items/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }
    @Test
    public void testDelete() throws Exception{
        Mockito.when(
                mockItemService.remove(ArgumentMatchers.anyInt())
        ).thenReturn(true);

        mvc.perform(MockMvcRequestBuilders.delete("/rmdaily/v1/items/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }
    @Test
    public void testEditItemWithoutItem() throws Exception {
        Mockito.when(
                mockItemService.edit(ArgumentMatchers.anyInt(), ArgumentMatchers.anyString())
        ).thenReturn(null);

        String requestBody = "{\"name\": \"raclette\"}";
        mvc.perform(MockMvcRequestBuilders.put("/rmdaily/v1/items/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotFound())
                .andReturn();
    }
    @Test
    public void testEditItem() throws Exception {
        Item item = new Item();
        item.setId(1);
        item.setName("raclette");
        Mockito.when(
                mockItemService.edit(ArgumentMatchers.anyInt(), ArgumentMatchers.anyString())
        ).thenReturn(item);

        String requestBody = "{\"name\": \"raclette\"}";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/rmdaily/v1/items/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn();
        String responseBody = result.getResponse().getContentAsString();
        Assertions.assertTrue(responseBody.contains("raclette"));
    }
    @Test
    public void testSwitchStatusWithoutService() throws Exception {
        Mockito.when(
                mockItemService.switchStatus(ArgumentMatchers.anyInt())
        ).thenReturn(false);

        mvc.perform(MockMvcRequestBuilders.put("/rmdaily/v1/items/1/state")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

    }
    @Test
    public void testSwitchStatus() throws Exception {
        Mockito.when(
                mockItemService.switchStatus(ArgumentMatchers.anyInt())
        ).thenReturn(true);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/rmdaily/v1/items/1/state")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }


}
