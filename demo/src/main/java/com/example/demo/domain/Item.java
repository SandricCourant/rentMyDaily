package com.example.demo.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Collection;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private Boolean status;
    @JsonIgnore
    @ManyToMany
    private Collection<SubCategory> subCategories;
    @ManyToOne
    @JsonIgnore
    private Owner owner;
    private String description;
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

    public Collection<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(Collection<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", description='" + description + '\'' +
                '}';
    }
}
