package com.example.demo.services;

import com.example.demo.domain.Category;
import com.example.demo.domain.SubCategory;

public interface SubCategoryService {
    public SubCategory createSubCategory(Category category, String name);
}
