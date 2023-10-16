package com.andrew.FinancialHelper.service;

import com.andrew.FinancialHelper.db.entity.Category;
import com.andrew.FinancialHelper.db.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenValidCategoryId_whenFindingCategoryById_thenCategoryIsFound() {
        // Given
        long categoryId = 1L;
        Category category = new Category();
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        // When
        Category foundCategory = categoryService.findCategoryById(categoryId);

        // Then
        assertNotNull(foundCategory);
        assertEquals(category, foundCategory);
    }

    @Test
    void givenInvalidCategoryId_whenFindingCategoryById_thenEntityNotFoundExceptionThrown() {
        // Given
        long categoryId = 1L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // When and Then
        assertThrows(EntityNotFoundException.class, () -> categoryService.findCategoryById(categoryId));
    }

    @Test
    void givenValidCategory_whenCreatingCategory_thenCategoryIsCreated() {
        // Given
        Category category = new Category();
        category.setName("Test Category");

        Category savedCategory = new Category();
        when(categoryRepository.save(any(Category.class))).thenReturn(savedCategory);

        // When
        Category createdCategory = categoryService.createCategory(category);

        // Then
        assertNotNull(createdCategory);
        assertEquals(savedCategory, createdCategory);
    }

    @Test
    void givenValidCategory_whenUpdatingCategory_thenCategoryIsUpdated() {
        // Given
        Category category = new Category();
        category.setId(1L);
        category.setName("Updated Category");

        Category existingCategory = new Category();
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(any(Category.class))).thenReturn(existingCategory);

        // When
        categoryService.updateCategory(category);

        // Then
        assertEquals("Updated Category", existingCategory.getName());
    }

    @Test
    void givenInvalidCategory_whenUpdatingCategory_thenEntityNotFoundExceptionThrown() {
        // Given
        Category category = new Category();
        category.setId(1L);

        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        // When and Then
        assertThrows(EntityNotFoundException.class, () -> categoryService.updateCategory(category));
    }

    @Test
    void givenValidCategoryId_whenDeletingCategory_thenCategoryIsDeleted() {
        // Given
        long categoryId = 1L;
        when(categoryRepository.existsById(categoryId)).thenReturn(true);

        // When
        categoryService.deleteCategory(categoryId);

        // Then
        verify(categoryRepository, times(1)).deleteById(categoryId);
    }

    @Test
    void givenInvalidCategoryId_whenDeletingCategory_thenEntityNotFoundExceptionThrown() {
        // Given
        long categoryId = 1L;
        when(categoryRepository.existsById(categoryId)).thenReturn(false);

        // When and Then
        assertThrows(EntityNotFoundException.class, () -> categoryService.deleteCategory(categoryId));
    }
}