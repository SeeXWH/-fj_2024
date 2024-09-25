package org.example.lesson_5_spring.repository;

import lombok.Data;
import org.example.lesson_5_spring.model.Categories;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
@Data
public class CategoriesRepository {
    private HashMap<Long, Categories> categoriesMap = new HashMap<>();

    public List<Categories> findAll() {
        List<Categories> categoriesList = new ArrayList<>();
        categoriesMap.forEach((k, v) -> categoriesList.add(v));
        return categoriesList;
    }

    public Categories findById(Long id) {
        return categoriesMap.get(id);
    }

    public Categories save(Categories categories) {
        return categoriesMap.put(categories.getId(), categories);
    }

    public void deleteById(Long id) {
        categoriesMap.remove(id);
    }

    public boolean existsById(Long id) {
        return categoriesMap.containsKey(id);
    }
}
