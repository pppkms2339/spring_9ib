package com.example.todo.controller;

import com.example.todo.entity.ToDo;
import com.example.todo.repository.CommonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api")
public class ToDoController {

    private CommonRepository<ToDo> repository;

    public ToDoController(CommonRepository<ToDo> repository) {
        this.repository = repository;
    }

    @GetMapping("/todos")
    public ResponseEntity<Collection<ToDo>> findAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping("/todo")
    public ResponseEntity<ToDo> save(@RequestBody ToDo entity) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(repository.save(entity));
    }

    @GetMapping("/todo/{id}")
    public ResponseEntity<ToDo> findById(@PathVariable String id) {
        return ResponseEntity.ok(repository.findById(id));
    }

    @GetMapping("/todo")
    public ResponseEntity<ToDo> findById2(@RequestParam String id) {
        return ResponseEntity.ok(repository.findById(id));
    }

}
