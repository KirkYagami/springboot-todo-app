package com.nick.taskmanager.controller;

import com.nick.taskmanager.model.Task;
import com.nick.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // ── Helper: extract username from Spring Security context ─────────
    private String username(Authentication auth) {
        return auth.getName();
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task, Authentication auth) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(taskService.createTask(task, username(auth)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(Authentication auth) {
        try {
            List<Task> tasks = taskService.getAllTasks(username(auth));
            return tasks.isEmpty()
                    ? ResponseEntity.noContent().build()
                    : ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id, Authentication auth) {
        Optional<Task> task = taskService.getTaskById(id, username(auth));
        return task.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id,
                                           @RequestBody Task task,
                                           Authentication auth) {
        Task updated = taskService.updateTask(id, task, username(auth));
        return updated != null
                ? ResponseEntity.ok(updated)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id, Authentication auth) {
        try {
            return taskService.deleteTask(id, username(auth))
                    ? ResponseEntity.noContent().build()
                    : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<Task> markComplete(@PathVariable Long id, Authentication auth) {
        Task task = taskService.markComplete(id, username(auth));
        return task != null
                ? ResponseEntity.ok(task)
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/status/{completed}")
    public ResponseEntity<List<Task>> getTasksByStatus(@PathVariable boolean completed,
                                                       Authentication auth) {
        try {
            List<Task> tasks = taskService.getTasksByStatus(completed, username(auth));
            return tasks.isEmpty()
                    ? ResponseEntity.noContent().build()
                    : ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Task>> searchTasks(@RequestParam String keyword,
                                                  Authentication auth) {
        try {
            List<Task> tasks = taskService.searchTasks(keyword, username(auth));
            return tasks.isEmpty()
                    ? ResponseEntity.noContent().build()
                    : ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}