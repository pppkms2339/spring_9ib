package com.example.todo.entity;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ToDo {

    private String id;

    @NotNull
    private String description;

    private LocalDateTime created;

    private LocalDateTime updated;

    private boolean completed;

    public ToDo() {
        this.id = UUID.randomUUID().toString();
        this.created = LocalDateTime.now();
        this.updated = this.created;
    }

}
