package com.example.demo.ut.servicesTests;

import com.example.demo.domain.Category;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.services.CategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryServiceTest {
    @Autowired
    private CategoryService categoryService;
    @MockBean
    private CategoryRepository mockCategoryRepository;

    @Test
    public void testCreateCategory(){
        Category category = new Category();
        category.setId(999);
        category.setName("category");
        Mockito.when(
                mockCategoryRepository.save(ArgumentMatchers.any(Category.class)))
                .thenReturn(category);
        Category result = categoryService.createCategory("Mobile");
        Assertions.assertEquals(999, result.getId());
    }
    @Test
    public void testFindById(){
        Category category = new Category();
        category.setId(999);
        Mockito.when(
                        mockCategoryRepository.findById(ArgumentMatchers.anyInt()))
                .thenReturn(Optional.of(category));
        Category result = categoryService.findById(0);
        Assertions.assertEquals(999, result.getId());
    }

}
