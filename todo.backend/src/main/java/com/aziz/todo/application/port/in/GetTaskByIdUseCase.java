
package com.aziz.todo.application.port.in;

import com.aziz.todo.application.port.out.TaskRepository;
import com.aziz.todo.domain.exception.TaskNotFoundException;
import com.aziz.todo.domain.model.Task;
import org.springframework.stereotype.Service;

@Service
public class GetTaskByIdUseCase {

    private final TaskRepository taskRepository;

    public GetTaskByIdUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() ->
                        new TaskNotFoundException(
                                "Task not found with id: " + id
                        )
                );
    }
}
