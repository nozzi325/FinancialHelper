package com.andrew.FinancialHelper.service;


import com.andrew.FinancialHelper.db.entity.Category;
import com.andrew.FinancialHelper.db.repository.CategoryRepository;
import com.andrew.FinancialHelper.exception.AccountNotFoundException;
import com.andrew.FinancialHelper.exception.CategoryNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category findCategoryById(Long id){
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(String.format("Category with id %d not found",id)));
    }

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    @Transactional
    public void createCategory(Category category){
        categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(Long id){
        if (!categoryRepository.existsById(id)){
            throw new CategoryNotFoundException(String.format("Category with id %d not found", id));
        }
        categoryRepository.deleteById(id);
    }

    @Transactional
    public void updateCategory(Category category){
        if (!categoryRepository.existsById(category.getId())){
            throw new CategoryNotFoundException(String.format("Category with id %d not found", category.getId()));
        }
        categoryRepository.save(category);
    }
}
