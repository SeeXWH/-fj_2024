package org.example.lesson_5_spring.service;

import org.example.lesson_5_spring.model.Categories;
import org.example.lesson_5_spring.repository.CategoriesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoriesServiceTest {

    @Mock
    private CategoriesRepository categoriesRepository;

    @InjectMocks
    private CategoriseService categoriseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCategories_shouldReturnListOfCategories() {
        List<Categories> categoriesList = Arrays.asList(new Categories(), new Categories());
        when(categoriesRepository.findAll()).thenReturn(categoriesList);

        List<Categories> result = categoriseService.getAllCategories();

        assertEquals(categoriesList, result);
    }

    @Test
    void getCategoriesById_shouldReturnCategories_whenCategoriesExists() {
        long id = 1L;
        Categories categories = new Categories();
        categories.setId(id);
        when(categoriesRepository.existsById(id)).thenReturn(true);
        when(categoriesRepository.findById(id)).thenReturn(categories);

        Categories result = categoriseService.getCategoriesById(id);

        assertEquals(categories, result);
    }

    @Test
    void getCategoriesById_shouldThrowRuntimeException_whenCategoriesNotFound() {
        long id = 1L;
        when(categoriesRepository.existsById(id)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> categoriseService.getCategoriesById(id));
    }

    @Test
    void addCategories_shouldSaveCategories_whenCategoriesDoesNotExist() {
        Categories categories = new Categories();
        categories.setId(1L);
        when(categoriesRepository.existsById(categories.getId())).thenReturn(false);
        when(categoriesRepository.save(categories)).thenReturn(categories);

        Categories result = categoriseService.addCategories(categories);

        assertEquals(categories, result);
    }

    @Test
    void addCategories_shouldThrowRuntimeException_whenCategoriesAlreadyExists() {
        Categories categories = new Categories();
        categories.setId(1L);
        when(categoriesRepository.existsById(categories.getId())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> categoriseService.addCategories(categories));
    }

    @Test
    void updateCategories_shouldSaveCategories_whenCategoriesExists() {
        Categories categories = new Categories();
        categories.setId(1L);
        when(categoriesRepository.existsById(categories.getId())).thenReturn(true);
        when(categoriesRepository.save(categories)).thenReturn(categories);

        Categories result = categoriseService.updateCategories(categories);

        assertEquals(categories, result);
    }

    @Test
    void updateCategories_shouldThrowRuntimeException_whenCategoriesNotFound() {
        Categories categories = new Categories();
        categories.setId(1L);
        when(categoriesRepository.existsById(categories.getId())).thenReturn(false);

        assertThrows(RuntimeException.class, () -> categoriseService.updateCategories(categories));
    }

    @Test
    void deleteCategories_shouldDeleteCategories_whenCategoriesExists() {
        long id = 1L;
        when(categoriesRepository.existsById(id)).thenReturn(true);

        assertDoesNotThrow(() -> categoriseService.deleteCategories(id));
        verify(categoriesRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteCategories_shouldThrowRuntimeException_whenCategoriesNotFound() {
        long id = 1L;
        when(categoriesRepository.existsById(id)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> categoriseService.deleteCategories(id));
    }
}