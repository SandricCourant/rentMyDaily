package com.example.demo.services;

import com.example.demo.domain.Item;
import com.example.demo.domain.Owner;
import com.example.demo.exceptions.ItemNotFoundException;

public interface ItemService {
    Item saveItem(String name, String description, Owner user);

    Iterable<Item> getItems();

    void remove(int id) throws ItemNotFoundException;

    Item getItem(int id) throws ItemNotFoundException;
}
