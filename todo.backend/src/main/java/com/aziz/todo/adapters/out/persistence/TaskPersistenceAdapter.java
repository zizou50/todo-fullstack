
package com.aziz.todo.adapters.out.persistence;

import com.aziz.todo.adapters.out.persistence.entity.TaskEntity;
import com.aziz.todo.adapters.out.persistence.mapper.TaskMapper;
import com.aziz.todo.adapters.out.persistence.repository.TaskJpaRepository;
import com.aziz.todo.application.port.out.TaskRepository;
import com.aziz.todo.domain.model.Task;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TaskPersistenceAdapter implements TaskRepository {

    private final TaskJpaRepository taskJpaRepository;

    public TaskPersistenceAdapter(TaskJpaRepository taskJpaRepository) {
        this.taskJpaRepository = taskJpaRepository;
    }

    @Override
    public Task save(Task task) {
        TaskEntity entity = TaskMapper.toEntity(task);
        TaskEntity savedEntity = taskJpaRepository.save(entity);
        return TaskMapper.toDomain(savedEntity);
    }

    @Override
    public List<Task> findAll() {
        return taskJpaRepository.findAll()
                .stream()
                .map(TaskMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Task> findById(Long id) {
        return taskJpaRepository.findById(id)
                .map(TaskMapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        taskJpaRepository.deleteById(id);
    }
}
