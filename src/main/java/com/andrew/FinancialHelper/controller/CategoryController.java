package com.andrew.FinancialHelper.controller;

import com.andrew.FinancialHelper.db.entity.Category;
import com.andrew.FinancialHelper.dto.request.CategoryRequest;
import com.andrew.FinancialHelper.dto.response.CategoryResponse;
import com.andrew.FinancialHelper.service.CategoryService;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @GetMapping
    public List<CategoryResponse> getCategories(){
        return categoryService.getAllCategories()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    @GetMapping("{id}")
    public CategoryResponse getCategoryById(@PathVariable Long id){
        return convertToResponse(categoryService.findCategoryById(id));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createCategory(@RequestBody @Valid CategoryRequest request){
        categoryService.createCategory(convertToEntity(request));
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<HttpStatus> updateCategory(@RequestBody @Valid CategoryRequest request){
        categoryService.updateCategory(convertToEntity(request));
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    private Category convertToEntity(CategoryRequest request) {
        return modelMapper.map(request,Category.class);
    }

    private CategoryResponse convertToResponse(Category category) {
        return modelMapper.map(category,CategoryResponse.class);
    }
}
