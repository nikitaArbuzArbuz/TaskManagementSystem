package ru.effective.mobile.java.taskmanagementsystem.adapter.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.effective.mobile.java.taskmanagementsystem.app.domain.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findAllByAuthorIdOrExecutorId(Long authorId, Long executorId, Pageable pageable);
}
