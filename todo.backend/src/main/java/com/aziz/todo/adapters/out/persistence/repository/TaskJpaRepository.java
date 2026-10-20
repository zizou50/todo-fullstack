
package com.aziz.todo.adapters.out.persistence.repository;

import com.aziz.todo.adapters.out.persistence.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskJpaRepository
        extends JpaRepository<TaskEntity, Long> {
}
