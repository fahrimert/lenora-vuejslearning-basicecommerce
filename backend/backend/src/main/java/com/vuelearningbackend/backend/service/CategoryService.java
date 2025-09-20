package com.vuelearningbackend.backend.service;

import com.vuelearningbackend.backend.model.Category;
import com.vuelearningbackend.backend.repository.CategoryRepository;
import com.vuelearningbackend.backend.request.UpdateCategoryRequest;
import com.vuelearningbackend.backend.service.UserInterfaces.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category addCategory(Category category) {

        return Optional.of(category)
                .filter( c -> !categoryRepository.existsByName(c.getName()))
                .map(categoryRepository::save).orElseThrow(() -> new RuntimeException(category.getName() +"Already exists" ));
    }

    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryByName(String name) {
        return (Category) categoryRepository.findByName(name);
    }

    @Override
    public void deleteCategoryById(Long id) {
    categoryRepository.findById(id).ifPresentOrElse(categoryRepository::delete, () -> {
        throw new RuntimeException("Category not found");
    });
    }

    @Override
    public Category updateCategory(UpdateCategoryRequest categoryRequest, Long categoryId) {
        return Optional.ofNullable(getCategoryById(categoryId)).map(oldCategory -> {
            oldCategory.setName(categoryRequest.getName());
            return   categoryRepository.save(oldCategory);
        })
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Override
    public Category getCategoryById(Long id) {
        return  categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category Not Found"));
    }
}
