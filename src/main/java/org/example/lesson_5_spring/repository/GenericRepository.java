package org.example.lesson_5_spring.repository;

import java.util.List;

public interface GenericRepository<T, ID> {

    List<T> findAll();

    T findById(ID id);

    T save(T entity);

    void deleteById(ID id);

    boolean existsById(ID id);
}
