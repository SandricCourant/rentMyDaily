package com.example.demo.services;

import com.example.demo.domain.Category;

public interface CategoryService {
    public Category createCategory(String name);
    public Category findById(int id);
}
