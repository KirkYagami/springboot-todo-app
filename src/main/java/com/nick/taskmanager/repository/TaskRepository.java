package com.nick.taskmanager.repository;

import com.nick.taskmanager.model.Task;
import com.nick.taskmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // All tasks for a given user
    List<Task> findByUser(User user);

    // Filter by completion status for a user
    List<Task> findByUserAndCompleted(User user, boolean completed);

    // Search by title keyword for a user
    List<Task> findByUserAndTitleContainingIgnoreCase(User user, String keyword);

    // Safe single-task lookup that also verifies ownership
    Optional<Task> findByIdAndUser(Long id, User user);
}