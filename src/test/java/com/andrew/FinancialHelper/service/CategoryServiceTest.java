package com.andrew.FinancialHelper.service;

import com.andrew.FinancialHelper.db.entity.Category;
import com.andrew.FinancialHelper.db.repository.CategoryRepository;
import com.andrew.FinancialHelper.exception.CategoryNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    @InjectMocks CategoryService subj;
    @Mock CategoryRepository categoryRepository;

    @Test
    void shouldGetAllCategories() {
        subj.getAllCategories();

        verify(categoryRepository).findAll();
    }

    @Test
    void shouldGetCategoryById() {
        Long id = 1L;
        Category category = new Category();
        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

        subj.findCategoryById(id);

        verify(categoryRepository).findById(id);
        assertSame(category,categoryRepository.findById(id).get());
    }

    @Test
    void shouldThrowCategoryNotFoundExceptionWhenCategoryIdDoesNotExist() {
        Long id = 1L;
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class,() -> subj.findCategoryById(id));
    }

    @Test
    void shouldDeleteCategory() {
        Long id = 1L;
        when(categoryRepository.existsById(id)).thenReturn(true);

        subj.deleteCategory(id);

        verify(categoryRepository).deleteById(id);
    }

    @Test
    void shouldCreateCategory() {
        Category category = new Category();

        subj.createCategory(category);

        ArgumentCaptor<Category> categoryArgumentCaptor =
                ArgumentCaptor.forClass(Category.class);
        verify(categoryRepository).save(categoryArgumentCaptor.capture());
        Category capturedCategory = categoryArgumentCaptor.getValue();
        assertEquals(category, capturedCategory);
    }

    @Test
    void shouldThrowCategoryNotFoundExceptionWhenDeleteCategoryNotFound() {
        Long id = 1L;
        when(categoryRepository.existsById(id)).thenReturn(false);

        Assertions.assertThrows(CategoryNotFoundException.class,() -> subj.deleteCategory(id));
        verify(categoryRepository, never()).deleteById(any());
    }

    @Test
    void shouldUpdateCategory() {
        Long id = 1L;
        Category category = new Category();
        category.setId(id);
        when(categoryRepository.existsById(id)).thenReturn(true);

        subj.updateCategory(category);

        ArgumentCaptor<Category> categoryArgumentCaptor =
                ArgumentCaptor.forClass(Category.class);
        verify(categoryRepository).save(categoryArgumentCaptor.capture());
        Category capturedCategory = categoryArgumentCaptor.getValue();
        assertEquals(category, capturedCategory);
    }

    @Test
    void shouldThrowCategoryNotFoundExceptionWhenUpdateCategoryNotFound() {
        Long id = 1L;
        Category category = new Category();
        category.setId(id);
        when(categoryRepository.existsById(id)).thenReturn(false);

        assertThrows(CategoryNotFoundException.class,() -> subj.updateCategory(category));
        verify(categoryRepository, never()).save(any());
    }
}