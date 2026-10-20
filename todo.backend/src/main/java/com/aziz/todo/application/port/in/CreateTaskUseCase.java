
package com.aziz.todo.application.port.in;

import com.aziz.todo.application.port.out.TaskRepository;
import com.aziz.todo.domain.model.Task;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CreateTaskUseCase {

    private final TaskRepository taskRepository;

    public CreateTaskUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(String title, String description) {
        Task task = new Task(
                null,
                title,
                description,
                false,
                LocalDateTime.now()
        );

        return taskRepository.save(task);
    }
}
