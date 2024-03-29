package com.example.demo.it;

import com.example.demo.domain.Item;
import com.example.demo.domain.Owner;
import com.example.demo.repositories.ItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class ItemIntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    ItemRepository mockItemRepository;

    @Test
    public void testGetAllItems() throws Exception {
        //Defining the mock with Mockito
        Item item = new Item();
        item.setId(1);
        item.setName("waffle");
        Collection<Item> items = new ArrayList<>();
        items.add(item);
        Mockito.when(mockItemRepository.findAll()).thenReturn(items);

        //Testing
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/items/view"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        String responseBody = result.getResponse().getContentAsString();
        Assertions.assertTrue(responseBody.contains("\"name\":\"waffle\""));
    }
    @Test
    @WithMockUser()
    public void testCreateItem() throws Exception {
        //Defining the mock with Mockito
        Item item = new Item();
        item.setId(1);
        item.setName("waffle");
        Mockito.when(mockItemRepository.save(ArgumentMatchers.any(Item.class))).thenReturn(item);

        //Testing
        String requestBody = "{\"name\":\"squeegee\",\"description\":\"a squeegee to rent\"}";
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
    public void testRemoveItem() throws Exception {
        //Defining the mock with Mockito
        Item item = new Item();
        item.setId(1);
        item.setName("waffle");
        Mockito.when(mockItemRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(item));

        //Testing
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/items/9/remove")).andExpect(MockMvcResultMatchers.status().isNoContent()).andReturn();
        int responseStatus = result.getResponse().getStatus();
        Assertions.assertEquals(204, responseStatus);
    }
    @Test
    @WithMockUser()
    public void testRemoveItemNotFound() throws Exception {
        //Defining the mock with Mockito
        Mockito.when(mockItemRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());

        //Testing
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/items/9/remove")).andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
        String responseError = result.getResponse().getErrorMessage();
        assert responseError != null;
        Assertions.assertTrue(responseError.contains("Item not found"));
    }

    @Test
    @WithMockUser()
    public void testModifyItem() throws Exception {
        //Defining the mock with Mockito
        Item item = new Item();
        item.setId(1);
        item.setName("waffle");
        Owner owner = new Owner();
        owner.setId(1);
        item.setUser(owner);
        Mockito.when(mockItemRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(item));
        Mockito.when(mockItemRepository.save(ArgumentMatchers.any(Item.class))).thenReturn(item);
        //Testing
        String requestBody = "{\"name\":\"squeegee\",\"description\":\"a good item\"}";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/items/9/update").contentType(MediaType.APPLICATION_JSON).content(requestBody)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String responseBody = result.getResponse().getContentAsString();
        Assertions.assertTrue(responseBody.contains("\"name\":\"squeegee\""));
    }
    @Test
    @WithMockUser()
    public void testModifyItemNotFound() throws Exception {
        //Defining the mock with Mockito
        Mockito.when(mockItemRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        //Testing
        String requestBody = "{\"name\":\"squeegee\",\"description\":\"a good item\"}";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/items/9/update").contentType(MediaType.APPLICATION_JSON).content(requestBody)).andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
        String responseError = result.getResponse().getErrorMessage();
        assert responseError != null;
        Assertions.assertTrue(responseError.contains("Item not found"));
    }
}
