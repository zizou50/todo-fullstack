package com.aziz.todo.adapters.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TaskRequest(

        @NotBlank(message = "Task title is required.")
        @Size(max = 150, message = "Task title must not exceed 150 characters.")
        String title,

        @Size(max = 1000, message = "Task description must not exceed 1000 characters.")
        String description,

        Boolean completed

) {}
