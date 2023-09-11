package com.example.demo.controllers;

import com.example.demo.domain.Item;
import com.example.demo.domain.Owner;
import com.example.demo.dto.ItemDto;
import com.example.demo.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CrossOrigin()
@RestController
@RequestMapping("/api/v1/items")
public class ItemController {
    @Autowired
    ItemService itemService;

    @GetMapping("view")
    public ResponseEntity<Iterable<Item>> getAllItems() {
        return ResponseEntity.ok().body(itemService.getItems());
    }

    @PostMapping("add")
    public ResponseEntity<Item> addItem(@AuthenticationPrincipal Owner user, @RequestBody ItemDto itemDto) {
        Item newItem = itemService.saveItem(itemDto.getName(), itemDto.getDescription(), user);

        return ResponseEntity.status(HttpStatus.CREATED).body(newItem);
    }
}
