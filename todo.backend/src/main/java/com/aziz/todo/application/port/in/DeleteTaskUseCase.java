
package com.aziz.todo.application.port.in;

import com.aziz.todo.application.port.out.TaskRepository;
import com.aziz.todo.domain.exception.TaskNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DeleteTaskUseCase {

    private final TaskRepository taskRepository;

    public DeleteTaskUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void deleteTask(Long id) {
        taskRepository.findById(id)
                .orElseThrow(() ->
                        new TaskNotFoundException(
                                "Task not found with id: " + id
                        )
                );

        taskRepository.deleteById(id);
    }
}
