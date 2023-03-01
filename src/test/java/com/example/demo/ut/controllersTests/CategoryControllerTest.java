package com.example.demo.ut.controllersTests;

import com.example.demo.controllers.CategoryController;
import com.example.demo.domain.Category;
import com.example.demo.domain.SubCategory;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {
    @Autowired
    private CategoryController categoryController;
    @Autowired
    private MockMvc mvc;
    @MockBean
    private CategoryService mockCategoryService;
    @MockBean
    private SubCategoryService mockSubCategoryService;
    @Test
    public void testCreateCategory() throws Exception {
        Category category = new Category();
        category.setId(1);
        category.setName("Mobile");
        Mockito.when(
                mockCategoryService.createCategory(ArgumentMatchers.anyString())
        ).thenReturn(category);

        String requestBody = "{\"name\": \"Mobile\"}";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/rmdaily/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Assertions.assertTrue(responseBody.contains("Mobile"));
    }
    @Test
    public void testCreateSubCategory() throws Exception {
        Mockito.when(
                mockCategoryService.findById(ArgumentMatchers.anyInt())
        ).thenReturn(new Category());

        SubCategory subCategory = new SubCategory();
        subCategory.setId(1);
        subCategory.setName("Nokia");
        Mockito.when(
                mockSubCategoryService.createSubCategory(ArgumentMatchers.any(Category.class), ArgumentMatchers.anyString())
        ).thenReturn(subCategory);

        String requestBody = "{\"name\": \"Nokia\"}";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/rmdaily/v1/categories/1/subcategories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Assertions.assertTrue(responseBody.contains("Nokia"));
    }
    @Test
    public void testCreateSubCategoryWithoutCategories() throws Exception {
        Mockito.when(
                mockCategoryService.findById(ArgumentMatchers.anyInt())
        ).thenReturn(null);

        String requestBody = "{\"name\": \"Nokia\"}";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/rmdaily/v1/categories/1/subcategories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotFound())
                .andReturn();
    }

}
