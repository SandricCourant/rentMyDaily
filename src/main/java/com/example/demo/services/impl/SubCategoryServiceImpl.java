package com.example.demo.services.impl;

import com.example.demo.domain.Category;
import com.example.demo.domain.SubCategory;
import com.example.demo.repositories.SubCategoryRepository;
import com.example.demo.services.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubCategoryServiceImpl implements SubCategoryService {
    @Autowired
    private SubCategoryRepository subCategoryRepository;
    @Override
    public SubCategory createSubCategory(Category category, String name) {
        SubCategory subCategory = new SubCategory();
        subCategory.setCategory(category);
        subCategory.setName(name);
        return subCategoryRepository.save(subCategory);
    }
}
