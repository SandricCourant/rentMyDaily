package com.example.demo.ut.servicesTests;

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

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class ItemServiceTest {
    @Autowired
    private ItemService itemService;
    @MockBean
    private ItemRepository mockItemRepository;
    @Test
    public void testCreatedItem(){
        Owner owner = new Owner();
        Item item = new Item();
        item.setId(999);
        Mockito.when(
                mockItemRepository.save(ArgumentMatchers.any(Item.class))
        ).thenReturn(item);
        Item result = itemService.createdItem(owner, "raclette");
        Assertions.assertEquals(999, result.getId());
    }
    @Test
    public void testIsRemove(){
        Item item = new Item();
        item.setId(999);
        Mockito.when(
                mockItemRepository.findById(ArgumentMatchers.anyInt())
        ).thenReturn(Optional.of(item));
        boolean result = itemService.remove(0);
        Assertions.assertTrue(result);
    }
    @Test
    public void testIsNotRemove(){
        Mockito.when(
                mockItemRepository.findById(ArgumentMatchers.anyInt())
        ).thenReturn(Optional.empty());
        boolean result = itemService.remove(0);
        Assertions.assertFalse(result);
    }
    @Test
    public void testEditWithItemNotFound(){
        Mockito.when(
                mockItemRepository.findById(ArgumentMatchers.anyInt())
        ).thenReturn(Optional.empty());
        Item result = itemService.edit(0, "raclette");
        Assertions.assertNull(result);
    }
    @Test
    public void testEdit(){
        Item item = new Item();
        item.setId(999);
        Mockito.when(
                mockItemRepository.findById(ArgumentMatchers.anyInt())
        ).thenReturn(Optional.of(item));
        Mockito.when(
                mockItemRepository.save(item)
        ).thenReturn(item);
        Item result = itemService.edit(0, "raclette");
        Assertions.assertEquals(999, result.getId());
    }
    @Test
    public void testSwitchStatusWithItemNotFound(){
        Mockito.when(
                mockItemRepository.findById(ArgumentMatchers.anyInt())
        ).thenReturn(Optional.empty());
        Boolean result = itemService.switchStatus(0);
        Assertions.assertFalse(result);
    }
    @Test
    public void testSwitchStatus(){
        Item item = new Item();
        item.setId(999);
        item.setStatus(true);
        Mockito.when(
                mockItemRepository.findById(ArgumentMatchers.anyInt())
        ).thenReturn(Optional.of(item));
        Mockito.when(
                mockItemRepository.save(item)
        ).thenReturn(item);
        boolean result = itemService.switchStatus(0);
        Assertions.assertTrue(result);
    }
}
