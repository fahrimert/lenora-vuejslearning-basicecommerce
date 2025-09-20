package com.vuelearningbackend.backend.service.UserInterfaces;

import com.vuelearningbackend.backend.model.Category;
import com.vuelearningbackend.backend.request.UpdateCategoryRequest;

import java.util.List;

public interface ICategoryService {
    Category addCategory(Category category);
    List<Category> findAllCategories();
    Category getCategoryByName(String name);
    void  deleteCategoryById(Long id);
    Category updateCategory(UpdateCategoryRequest categoryRequest , Long categoryId);
    Category getCategoryById(Long id);
}
