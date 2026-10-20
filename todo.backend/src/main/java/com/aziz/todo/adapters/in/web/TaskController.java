package com.aziz.todo.adapters.in.web;

import com.aziz.todo.adapters.in.web.dto.TaskRequest;
import com.aziz.todo.adapters.in.web.dto.TaskResponse;
import com.aziz.todo.application.port.in.CreateTaskUseCase;
import com.aziz.todo.application.port.in.GetAllTasksUseCase;
import com.aziz.todo.application.port.in.GetTaskByIdUseCase;
import com.aziz.todo.application.port.in.UpdateTaskUseCase;
import com.aziz.todo.application.port.in.DeleteTaskUseCase;
import com.aziz.todo.application.port.in.ToggleTaskCompletionUseCase;
import com.aziz.todo.domain.model.Task;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final CreateTaskUseCase createTaskUseCase;
    private final GetAllTasksUseCase getAllTasksUseCase;
    private final GetTaskByIdUseCase getTaskByIdUseCase;
    private final UpdateTaskUseCase updateTaskUseCase;
    private final DeleteTaskUseCase deleteTaskUseCase;
    private final ToggleTaskCompletionUseCase toggleTaskCompletionUseCase;

    public TaskController(
            CreateTaskUseCase createTaskUseCase,
            GetAllTasksUseCase getAllTasksUseCase,
            GetTaskByIdUseCase getTaskByIdUseCase,
            UpdateTaskUseCase updateTaskUseCase,
            DeleteTaskUseCase deleteTaskUseCase,
            ToggleTaskCompletionUseCase toggleTaskCompletionUseCase) {

        this.createTaskUseCase = createTaskUseCase;
        this.getAllTasksUseCase = getAllTasksUseCase;
        this.getTaskByIdUseCase = getTaskByIdUseCase;
        this.updateTaskUseCase = updateTaskUseCase;
        this.deleteTaskUseCase = deleteTaskUseCase;
        this.toggleTaskCompletionUseCase = toggleTaskCompletionUseCase;
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @RequestBody @Valid TaskRequest request) {

        Task task = createTaskUseCase.createTask(
                request.title(),
                request.description()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(toResponse(task));
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks() {

        List<TaskResponse> tasks = getAllTasksUseCase.getAllTasks()
                .stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(
            @PathVariable Long id) {

        Task task = getTaskByIdUseCase.getTaskById(id);

        return ResponseEntity.ok(toResponse(task));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequest request) {

        Task task = updateTaskUseCase.updateTask(
                id,
                request.title(),
                request.description()
        );

        return ResponseEntity.ok(toResponse(task));
    }

    @PatchMapping("/{id}/completion")
    public ResponseEntity<TaskResponse> toggleTaskCompletion(
            @PathVariable Long id) {

        Task task = toggleTaskCompletionUseCase
                .toggleTaskCompletion(id);

        return ResponseEntity.ok(toResponse(task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long id) {

        deleteTaskUseCase.deleteTask(id);

        return ResponseEntity.noContent().build();
    }

    private TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.isCompleted(),
                task.getCreatedAt()
        );
    }
}
