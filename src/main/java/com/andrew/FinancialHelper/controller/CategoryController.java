package com.andrew.FinancialHelper.controller;

import com.andrew.FinancialHelper.db.entity.Category;
import com.andrew.FinancialHelper.dto.request.CategoryRequest;
import com.andrew.FinancialHelper.dto.response.CategoryResponse;
import com.andrew.FinancialHelper.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    public CategoryController(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<CategoryResponse> getAllCategories() {
        return categoryService.getAllCategories()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(@PathVariable Long id) {
        return convertToResponse(categoryService.findCategoryById(id));
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody @Valid CategoryRequest request) {
        Category category = categoryService.updateCategory(convertToEntity(request));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(convertToResponse(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@RequestBody @Valid CategoryRequest request) {
        Category category = categoryService.updateCategory(convertToEntity(request));
        return ResponseEntity
                .ok()
                .body(convertToResponse(category));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }

    private Category convertToEntity(CategoryRequest request) {
        return modelMapper.map(request, Category.class);
    }

    private CategoryResponse convertToResponse(Category category) {
        return modelMapper.map(category, CategoryResponse.class);
    }
}
