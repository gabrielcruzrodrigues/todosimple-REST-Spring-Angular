package com.example.todoTask.controllers;

import com.example.todoTask.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.todoTask.models.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/task")
@Validated
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<String>create(@Valid @RequestBody Task taskObj) {
        this.taskService.create(taskObj);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(taskObj.getId())
                .toUri();

        return ResponseEntity.created(uri).body("Tafera cadastrada");
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Task>> findAllByUserId(@PathVariable Long userId) {
        List<Task> tasks = this.taskService.findAllByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> findById(@PathVariable Long id) {
        Task task = taskService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> update(@Valid @RequestBody Task taskObj, @PathVariable Long id) {
        taskObj.setId(id);
        Task taskUpdate = taskService.update(taskObj);
        return ResponseEntity.status(HttpStatus.OK).body(taskUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        this.taskService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Task deletada.");
    }
}
