
package com.aziz.todo.adapters.out.persistence.mapper;

import com.aziz.todo.adapters.out.persistence.entity.TaskEntity;
import com.aziz.todo.domain.model.Task;

public final class TaskMapper {

    private TaskMapper() {
    }

    public static TaskEntity toEntity(Task task) {
        TaskEntity entity = new TaskEntity();
        entity.setId(task.getId());
        entity.setTitle(task.getTitle());
        entity.setDescription(task.getDescription());
        entity.setCompleted(task.isCompleted());
        entity.setCreatedAt(task.getCreatedAt());
        return entity;
    }

    public static Task toDomain(TaskEntity entity) {
        return new Task(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.isCompleted(),
                entity.getCreatedAt()
        );
    }
}
