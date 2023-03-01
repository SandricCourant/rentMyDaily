package com.example.demo.it;

import com.example.demo.controllers.ItemController;
import com.example.demo.domain.Item;
import com.example.demo.domain.Owner;
import com.example.demo.repositories.ItemRepository;
import com.example.demo.repositories.OwnerRepository;
import com.example.demo.services.ItemService;
import com.example.demo.services.OwnerService;
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

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ItemIntegrationTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ItemController itemController;
    @Autowired
    private ItemService itemService;
    @Autowired
    private OwnerService ownerService;
    @MockBean
    private ItemRepository mockItemRepository;
    @MockBean
    private OwnerRepository mockOwnerRepository;
    @Test
    public void testCreateItem() throws Exception {
        Item item = new Item();
        item.setId(1);
        item.setName("raclette");
        Mockito.when(
                mockOwnerRepository.findById(ArgumentMatchers.anyInt())
        ).thenReturn(Optional.of(new Owner()));
        Mockito.when(
                mockItemRepository.save(ArgumentMatchers.any(Item.class))
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
                mockOwnerRepository.findById(ArgumentMatchers.anyInt())
        ).thenReturn(Optional.empty());

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
                mockItemRepository.findById(ArgumentMatchers.anyInt())
        ).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.delete("/rmdaily/v1/items/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }
    @Test
    public void testDelete() throws Exception{
        Mockito.when(
                mockItemRepository.findById(ArgumentMatchers.anyInt())
        ).thenReturn(Optional.of(new Item()));


        mvc.perform(MockMvcRequestBuilders.delete("/rmdaily/v1/items/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }
    @Test
    public void testEditItemWithoutItem() throws Exception {
        Mockito.when(
                mockItemRepository.findById(ArgumentMatchers.anyInt())
        ).thenReturn(Optional.empty());

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
                mockItemRepository.findById(ArgumentMatchers.anyInt())
        ).thenReturn(Optional.of(item));
        Mockito.when(
                mockItemRepository.save(item)
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
                mockItemRepository.findById(ArgumentMatchers.anyInt())
        ).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.put("/rmdaily/v1/items/1/state")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

    }
    @Test
    public void testSwitchStatus() throws Exception {
        Item item = new Item();
        item.setId(999);
        item.setStatus(true);
        Mockito.when(
                mockItemRepository.findById(ArgumentMatchers.anyInt())
        ).thenReturn(Optional.of(item));
        Mockito.when(
                mockItemRepository.save(item)
        ).thenReturn(item);

        mvc.perform(MockMvcRequestBuilders.put("/rmdaily/v1/items/1/state")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }
}
