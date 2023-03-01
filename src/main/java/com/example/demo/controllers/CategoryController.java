package com.example.demo.controllers;

import com.example.demo.domain.Category;
import com.example.demo.domain.SubCategory;
import com.example.demo.dto.CreatedCategoryDto;
import com.example.demo.dto.CreatedSubCategoryDto;
import com.example.demo.exceptions.SubCategoryNotFoundException;
import com.example.demo.services.CategoryService;
import com.example.demo.services.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rmdaily/v1")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    SubCategoryService subCategoryService;
    @PostMapping("categories")
    public ResponseEntity<Category> createCategory(@RequestBody CreatedCategoryDto requestDto){
        Category category = categoryService.createCategory(requestDto.getName());


        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }
    @PostMapping("categories/{id}/subcategories")
    public ResponseEntity<SubCategory> createSubCategory(@PathVariable int id, @RequestBody CreatedSubCategoryDto requestDto) throws SubCategoryNotFoundException {
        Category category = categoryService.findById(id);
        if(category == null) throw new SubCategoryNotFoundException();

        SubCategory subCategory = subCategoryService.createSubCategory(category ,requestDto.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(subCategory);
    }

}
