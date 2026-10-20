
package com.aziz.todo.application.port.in;

import com.aziz.todo.application.port.out.TaskRepository;
import com.aziz.todo.domain.exception.TaskNotFoundException;
import com.aziz.todo.domain.model.Task;
import org.springframework.stereotype.Service;

@Service
public class UpdateTaskUseCase {

    private final TaskRepository taskRepository;

    public UpdateTaskUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task updateTask(Long id, String title, String description) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() ->
                        new TaskNotFoundException(
                                "Task not found with id: " + id
                        )
                );

        Task updatedTask = new Task(
                existingTask.getId(),
                title,
                description,
                existingTask.isCompleted(),
                existingTask.getCreatedAt()
        );

        return taskRepository.save(updatedTask);
    }
}
