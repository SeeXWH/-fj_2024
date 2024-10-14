package org.example.lesson_5_spring.repository;

import lombok.Data;
import org.example.lesson_5_spring.model.Categories;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
@Data
public class CategoriesRepository implements GenericRepository<Categories, Long> {
    private HashMap<Long, Categories> categoriesMap = new HashMap<>();

    @Override
    public List<Categories> findAll() {
        List<Categories> categoriesList = new ArrayList<>();
        categoriesMap.forEach((k, v) -> categoriesList.add(v));
        return categoriesList;
    }

    @Override
    public Categories findById(Long id) {
        return categoriesMap.get(id);
    }

    @Override
    public Categories save(Categories categories) {
        return categoriesMap.put(categories.getId(), categories);
    }

    @Override
    public void deleteById(Long id) {
        categoriesMap.remove(id);
    }

    @Override
    public boolean existsById(Long id) {
        return categoriesMap.containsKey(id);
    }
}
