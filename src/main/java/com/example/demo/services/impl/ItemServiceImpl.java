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
    private ItemRepository itemRepository;
    @Override
    public Item createdItem(Owner owner, String name){

        Item item = new Item();
        item.setStatus(false);
        item.setName(name);
        item.setUser(owner);

        return itemRepository.save(item);
    }
    @Override
    public boolean remove(int id){

        Item item = itemRepository.findById(id).orElse(null);

        if(item == null) return false;

        itemRepository.delete(item);
        return true;
    }
    @Override
    public Item edit(int id, String name){

        Item item = itemRepository.findById(id).orElse(null);

        if(item == null) return null;

        item.setName(name);
        return itemRepository.save(item);
    }
    @Override
    public boolean switchStatus(int id){
        Item item = itemRepository.findById(id).orElse(null);
        if(item == null) return false;
        item.setStatus(!item.getStatus());
        itemRepository.save(item);
        return true;
    }
}
