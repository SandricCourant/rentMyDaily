package com.example.demo.domain;


import jakarta.persistence.*;

import java.util.Collection;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private Boolean status;
    @ManyToMany
    private Collection<SubCategory> subCategories;
    @ManyToOne
    private Owner owner;
    private String descriptions;
    private Collection<String> photos;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Owner getUser() {
        return owner;
    }

    public void setUser(Owner owner) {
        this.owner = owner;
    }
}
