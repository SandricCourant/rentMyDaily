package com.example.demo.ut.servicesTests;

import com.example.demo.domain.Category;
import com.example.demo.domain.SubCategory;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.SubCategoryRepository;
import com.example.demo.services.CategoryService;
import com.example.demo.services.SubCategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
@SpringBootTest
@AutoConfigureMockMvc
public class SubCategoryServiceTest {
    @Autowired
    private SubCategoryService subCategoryService;
    @MockBean
    private SubCategoryRepository mockSubCategoryRepository;

    @Test
    public void testCreateSubCategory(){
        Category category = new Category();
        SubCategory subCategory = new SubCategory();
        subCategory.setId(999);
        Mockito.when(
                        mockSubCategoryRepository.save(ArgumentMatchers.any(SubCategory.class)))
                .thenReturn(subCategory);
        SubCategory result = subCategoryService.createSubCategory(category,"Nokia");
        Assertions.assertEquals(999, result.getId());
    }
}
