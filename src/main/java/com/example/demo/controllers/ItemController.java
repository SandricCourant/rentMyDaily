package com.example.demo.controllers;

import com.example.demo.domain.Item;
import com.example.demo.domain.Owner;
import com.example.demo.dto.CreatedItemDto;
import com.example.demo.dto.EditItemDto;
import com.example.demo.exceptions.ItemNotFoundException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.services.ItemService;
import com.example.demo.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rmdaily/v1")
public class ItemController {
    //TODO revoir les endpoints delete et put
    @Autowired
    private ItemService itemService;
    @Autowired
    private OwnerService ownerService;

    @PostMapping("users/{id}/items")
    public ResponseEntity<Item> createItem(@PathVariable int id,@RequestBody CreatedItemDto requestDto) throws UserNotFoundException {

        Owner owner = ownerService.findById(id);

        if(owner == null) throw new UserNotFoundException();

        Item item = itemService.createdItem(owner, requestDto.getName());

        return ResponseEntity.status(201).body(item);
    }
    @DeleteMapping("/items/{id}")
    public ResponseEntity<Boolean> deleteItem(@PathVariable int id) throws ItemNotFoundException{

        Boolean result = itemService.remove(id);

        if(!result) throw new ItemNotFoundException();

        return ResponseEntity.status(200).body(true);
    }
    @PutMapping("/items/{id}")
    public ResponseEntity<Item> editItem(@PathVariable int id, @RequestBody EditItemDto requestDto) throws ItemNotFoundException {

        Item item = itemService.edit(id, requestDto.getName());

        if(item == null) throw new ItemNotFoundException();

        return ResponseEntity.status(200).body(item);
    }
    @PutMapping("/items/{id}/state")
    public ResponseEntity<Boolean> switchStatus(@PathVariable int id) throws ItemNotFoundException{

        boolean result = itemService.switchStatus(id);

        if(!result) throw new ItemNotFoundException();

        return ResponseEntity.status(200).body(true);
    }

}
