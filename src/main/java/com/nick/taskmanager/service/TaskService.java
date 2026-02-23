package com.nick.taskmanager.service;

import com.nick.taskmanager.model.Task;
import com.nick.taskmanager.model.User;
import com.nick.taskmanager.repository.TaskRepository;
import com.nick.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    // ── Helper: resolve username → User entity ────────────────────────
    private User resolveUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    // ── CRUD ──────────────────────────────────────────────────────────

    public Task createTask(Task task, String username) {
        User user = resolveUser(username);
        task.setUser(user);
        return taskRepository.save(task);
    }

    public List<Task> getAllTasks(String username) {
        return taskRepository.findByUser(resolveUser(username));
    }

    public Optional<Task> getTaskById(Long id, String username) {
        return taskRepository.findByIdAndUser(id, resolveUser(username));
    }

    public Task updateTask(Long id, Task updatedTask, String username) {
        User user = resolveUser(username);
        return taskRepository.findByIdAndUser(id, user)
                .map(task -> {
                    task.setTitle(updatedTask.getTitle());
                    task.setDescription(updatedTask.getDescription());
                    task.setCompleted(updatedTask.isCompleted());
                    task.setPriority(updatedTask.getPriority());
                    task.setDueDate(updatedTask.getDueDate());
                    return taskRepository.save(task);
                })
                .orElse(null);
    }

    public boolean deleteTask(Long id, String username) {
        User user = resolveUser(username);
        Optional<Task> task = taskRepository.findByIdAndUser(id, user);
        if (task.isPresent()) {
            taskRepository.delete(task.get());
            return true;
        }
        return false;
    }

    public Task markComplete(Long id, String username) {
        User user = resolveUser(username);
        return taskRepository.findByIdAndUser(id, user)
                .map(task -> {
                    task.setCompleted(true);
                    return taskRepository.save(task);
                })
                .orElse(null);
    }

    public List<Task> getTasksByStatus(boolean completed, String username) {
        return taskRepository.findByUserAndCompleted(resolveUser(username), completed);
    }

    public List<Task> searchTasks(String keyword, String username) {
        return taskRepository.findByUserAndTitleContainingIgnoreCase(resolveUser(username), keyword);
    }
}