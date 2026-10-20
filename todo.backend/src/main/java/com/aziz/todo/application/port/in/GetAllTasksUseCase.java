
package com.aziz.todo.application.port.in;

import com.aziz.todo.application.port.out.TaskRepository;
import com.aziz.todo.domain.model.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllTasksUseCase {

    private final TaskRepository taskRepository;

    public GetAllTasksUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
}
