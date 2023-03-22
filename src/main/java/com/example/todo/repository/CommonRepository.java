package com.example.todo.repository;

import java.util.Collection;

public interface CommonRepository<T> {
    T save(T entity);
    Collection<T> saveAll(Collection<T> entities);
    void delete(T entity);
    T findById(String id);
    Collection<T> findAll();
    // CRUD
}
