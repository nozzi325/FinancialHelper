package com.andrew.FinancialHelper.service;


import com.andrew.FinancialHelper.db.entity.Category;
import com.andrew.FinancialHelper.db.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Category with id %d not found", id)));
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Transactional
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format("Category with id %d not found", id));
        }
        categoryRepository.deleteById(id);
    }

    @Transactional
    public Category updateCategory(Category updatedCategory) {
        Category existingCategory = findCategoryById(updatedCategory.getId());
        existingCategory.setName(updatedCategory.getName());
        return categoryRepository.save(existingCategory);
    }
}