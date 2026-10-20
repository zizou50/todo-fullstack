package com.aziz.todo.domain.model;

import java.time.LocalDateTime;

public class Task {

    private final Long id;
    private final String title;
    private final String description;
    private final boolean completed;
    private final LocalDateTime createdAt;

    public Task(
            Long id,
            String title,
            String description,
            boolean completed,
            LocalDateTime createdAt
    ) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException(
                "Le titre de la tâche est obligatoire."
            );
        }

        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}