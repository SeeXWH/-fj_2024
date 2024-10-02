package org.example.lesson_5_spring.repository;

import org.example.lesson_5_spring.model.Categories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoriesRepositoryTest {

    private CategoriesRepository categoriesRepository;

    @BeforeEach
    void setUp() {
        categoriesRepository = new CategoriesRepository();
    }

    @Test
    void findAll_shouldReturnEmptyList_whenRepositoryIsEmpty() {
        List<Categories> result = categoriesRepository.findAll();
        assertTrue(result.isEmpty());
    }

    @Test
    void findAll_shouldReturnListOfCategories_whenRepositoryIsNotEmpty() {
        Categories category1 = createTestCategory(1L);
        Categories category2 = createTestCategory(2L);
        categoriesRepository.save(category1);
        categoriesRepository.save(category2);

        List<Categories> result = categoriesRepository.findAll();

        assertEquals(2, result.size());
        assertTrue(result.contains(category1));
        assertTrue(result.contains(category2));
    }

    @Test
    void findById_shouldReturnNull_whenCategoryNotFound() {
        Categories result = categoriesRepository.findById(1L);
        assertNull(result);
    }

    @Test
    void findById_shouldReturnCategory_whenCategoryExists() {
        Categories category = createTestCategory(1L);
        categoriesRepository.save(category);

        Categories result = categoriesRepository.findById(1L);

        assertEquals(category, result);
    }

    @Test
    void save_shouldAddCategoryToRepository() {
        Categories category = createTestCategory(1L);
        categoriesRepository.save(category);

        assertTrue(categoriesRepository.existsById(1L));
        assertEquals(category, categoriesRepository.findById(1L));
    }

    @Test
    void deleteById_shouldRemoveCategoryFromRepository() {
        Categories category = createTestCategory(1L);
        categoriesRepository.save(category);
        categoriesRepository.deleteById(1L);

        assertFalse(categoriesRepository.existsById(1L));
    }

    @Test
    void existsById_shouldReturnFalse_whenCategoryNotFound() {
        assertFalse(categoriesRepository.existsById(1L));
    }

    @Test
    void existsById_shouldReturnTrue_whenCategoryExists() {
        Categories category = createTestCategory(1L);
        categoriesRepository.save(category);

        assertTrue(categoriesRepository.existsById(1L));
    }

    private Categories createTestCategory(Long id) {
        Categories category = new Categories();
        category.setId(id);
        return category;
    }
}