package com.example.todo.controller;

import com.example.todo.entity.ToDo;
import com.example.todo.repository.CommonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @RequestMapping(value = "/todo", method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<?> save(@RequestBody @Valid ToDo entity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
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

    @DeleteMapping("/todo")
    public ResponseEntity<?> delete(@RequestParam String id) {
        ToDo toDoFromDb = repository.findById(id);
        if (toDoFromDb == null) {
            return ResponseEntity.notFound().build();
        }
        repository.delete(toDoFromDb);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
