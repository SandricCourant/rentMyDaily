package com.example.demo.controllers;

import com.example.demo.domain.Item;
import com.example.demo.domain.Owner;
import com.example.demo.dto.RegisterRequestDto;
import com.example.demo.exceptions.UserAlreadyExistException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rmdaily/v1")
public class OwnerController {
    @Autowired
    private OwnerService ownerService;

    @GetMapping("/users/{id}")
    public ResponseEntity<Owner> getUser(@PathVariable int id) throws UserNotFoundException{

        Owner owner = ownerService.findById(id);

        if(owner == null) throw new UserNotFoundException();

        return ResponseEntity.status(200).body(owner);
    }
    @GetMapping("/users/{id}/items")
    public ResponseEntity<Iterable<Item>> getUserItem(@PathVariable int id) throws UserNotFoundException{

        Owner owner = ownerService.findById(id);

        if(owner == null) throw new UserNotFoundException();

        return ResponseEntity.status(200).body(owner.getItems());
    }
}
