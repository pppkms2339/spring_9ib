package com.example.todo.controller;

import com.example.todo.entity.ToDo;
import com.example.todo.repository.ToDoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    private final ToDoRepository repository;

    public MainController(ToDoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("todos", repository.findAll());
        return "index";
    }

    @PostMapping("/addTodo")
    public String addTodo(ToDo toDo) {
        repository.save(toDo);
        return "redirect:/";
    }

    @GetMapping("/addTodo")
    public String addTodo(Model model) {
        model.addAttribute("todo", new ToDo());
        return "todo";
    }

}
