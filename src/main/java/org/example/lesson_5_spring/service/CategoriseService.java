package org.example.lesson_5_spring.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.lesson_5_spring.model.Categories;
import org.example.lesson_5_spring.repository.CategoriesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
public class CategoriseService {
    private final CategoriesRepository categoriesRepository;

    public List<Categories> getAllCategories() {
        return categoriesRepository.findAll();
    }

    public Categories getCategoriesById(long id) {
        if (categoriesRepository.existsById(id)) {
            return categoriesRepository.findById(id);
        }
        else{
            throw new RuntimeException("Categories not found");
        }

    }

    public Categories addCategories(Categories categories) {
        if (categoriesRepository.existsById(categories.getId())) {
            throw new RuntimeException("Categories already exists");
        }
       return categoriesRepository.save(categories);
    }

    public Categories updateCategories(Categories categories) {
        if (categoriesRepository.existsById(categories.getId())){
            return categoriesRepository.save(categories);
        } else{
            throw new RuntimeException("Categories not found");
        }

    }

    public void deleteCategories(long id) {
        if (categoriesRepository.existsById(id)) {
            categoriesRepository.deleteById(id);
        } else{
            throw new RuntimeException("Categories not found");
        }
        categoriesRepository.deleteById(id);
    }

}
