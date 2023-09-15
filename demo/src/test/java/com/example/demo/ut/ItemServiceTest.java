package com.example.demo.ut;

import com.example.demo.DemoApplication;
import com.example.demo.domain.Item;
import com.example.demo.domain.Owner;
import com.example.demo.repositories.ItemRepository;
import com.example.demo.services.ItemService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
public class ItemServiceTest {
    @Autowired
    private ItemService itemService;

    @MockBean
    private ItemRepository mockItemRepository;

    @Test
    public void testSaveItem() throws Exception {
        // Defining the mock with Mockito

        Item item = new Item();
        item.setId(1);
        item.setName("waffle");

        Mockito.when(mockItemRepository.save(ArgumentMatchers.any(Item.class))).thenReturn(item);

        //Assertions
        Item result = itemService.saveItem("any", "anything", new Owner());
        Assertions.assertEquals("waffle", result.getName());
    }

    @Test
    public void testGetItems() throws Exception {
        // Defining the mock with Mockito
        List<Item> items = new ArrayList<>();

        Mockito.when(mockItemRepository.findAll()).thenReturn(items);

        //Assertions
        Iterable<Item> results = itemService.getItems();
        Assertions.assertIterableEquals(results, items);
    }
}
