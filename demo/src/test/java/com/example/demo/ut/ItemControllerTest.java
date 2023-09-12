package com.example.demo.ut;

import com.example.demo.controllers.ItemController;
import com.example.demo.domain.Item;
import com.example.demo.domain.Owner;
import com.example.demo.dto.ItemDto;
import com.example.demo.services.ItemService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class ItemControllerTest {
    @Autowired
    ItemController itemController;
    @MockBean
    ItemService mockItemService;

    @Test
    public void testGetAllItems() {
        //Defining the mock with Mockito
        Item item = new Item();
        item.setId(1);
        List<Item> items = new ArrayList<>();
        items.add(item);

        Mockito.when(mockItemService.getItems()).thenReturn(items);

        //Testing
        Iterable<Item> result = itemController.getAllItems().getBody();
        Assertions.assertIterableEquals(result, items);
    }

    @Test
    public void testAddItem() {
        //Defining the mock with Mockito
        Item item = new Item();
        item.setId(1);
        item.setName("waffle");
        Owner owner = new Owner();
        ItemDto itemDto = new ItemDto();
        itemDto.setDescription("I'm a DTO");

        Mockito.when(mockItemService.saveItem(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.any(Owner.class))).thenReturn(item);

        //Testing
        Item result = itemController.addItem(owner, itemDto).getBody();
        assert result != null;
        Assertions.assertEquals("waffle", result.getName());
    }
}
