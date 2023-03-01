package com.example.demo.services;

import com.example.demo.domain.Item;
import com.example.demo.domain.Owner;


public interface ItemService {
    public Item createdItem(Owner owner, String name);
    public boolean remove(int id);
    public Item edit(int id, String name);

    boolean switchStatus(int id);
}
