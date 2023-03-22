package com.example.todo.repository;

import com.example.todo.entity.ToDo;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ToDoRepository implements CommonRepository<ToDo> {

    private Map<String, ToDo> toDos = new HashMap<>();

    @Override
    public ToDo save(ToDo entity) {
        ToDo toDoFromDb = findById(entity.getId());
        if (toDoFromDb != null) {
            toDoFromDb.setDescription(entity.getDescription());
            toDoFromDb.setCompleted(entity.isCompleted());
            toDoFromDb.setUpdated(LocalDateTime.now());
            toDos.put(toDoFromDb.getId(), toDoFromDb);
            return toDoFromDb;
        }
        toDos.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Collection<ToDo> saveAll(Collection<ToDo> entities) {
        entities.forEach(this::save);
        return findAll();
    }

    @Override
    public void delete(ToDo entity) {
        toDos.remove(entity.getId());
    }

    @Override
    public ToDo findById(String id) {
        return toDos.get(id);
    }

    @Override
    public Collection<ToDo> findAll() {
        return toDos.values();
    }

}
