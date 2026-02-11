package com.nick.taskmanager.repository;

import com.nick.taskmanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Spring Data JPA auto-implements these methods!
    List<Task> findByCompleted(boolean completed);
    List<Task> findByTitleContaining(String keyword);
    List<Task> findByCompletedOrderByCreatedAtDesc(boolean completed);
}