package com.example.demo.services;

import com.example.demo.domain.Item;
import com.example.demo.domain.Owner;

public interface ItemService {
    Item saveItem(String name, String description, Owner user);

}
