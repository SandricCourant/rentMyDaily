package com.example.demo.services.impl;

import com.example.demo.domain.Item;
import com.example.demo.domain.Owner;
import com.example.demo.repositories.ItemRepository;
import com.example.demo.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Override
    public Item saveItem(String name, String description, Owner user) {
        Item newItem = new Item();

        //Add attributes
        newItem.setName(name);
        newItem.setDescription(description);
        newItem.setUser(user);
        newItem.setStatus(true);

        return itemRepository.save(newItem);
    }
}
