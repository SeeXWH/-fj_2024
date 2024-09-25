package org.example.lesson_5_spring.controller;

import lombok.AllArgsConstructor;
import org.example.lesson_5_spring.annotation.LogExecutionTime;
import org.example.lesson_5_spring.model.Categories;
import org.example.lesson_5_spring.repository.CategoriesRepository;
import org.example.lesson_5_spring.service.CategoriseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@LogExecutionTime
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/places/categories")
public class CategoriesController {
    private final CategoriseService categoryService;

    @GetMapping("/all")
    public List<Categories> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/byId/{id}")
    public Categories getCategoriesById(@PathVariable("id") long id) {
        return categoryService.getCategoriesById(id);
    }

    @PostMapping("/add")
    public Categories addCategories(@RequestBody Categories categories) {
        return categoryService.addCategories(categories);
    }

    @PutMapping("/update")
    public Categories updateCategories(@RequestBody Categories categories) {
        return categoryService.updateCategories(categories);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCategories(@PathVariable("id") long id) {
        categoryService.deleteCategories(id);
    }
}
