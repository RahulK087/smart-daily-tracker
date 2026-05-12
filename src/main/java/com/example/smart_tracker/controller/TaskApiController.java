package com.example.smart_tracker.controller;

import com.example.smart_tracker.entity.Task;
import com.example.smart_tracker.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskApiController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<Task> getAllTasks() {

        return taskService.getAllTasks();
    }

    @PostMapping
    public String addTask(@RequestBody Task task) {

        taskService.saveTask(task);

        return "Task Added Successfully";
    }

    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable Long id) {

        taskService.deleteTask(id);

        return "Task Deleted Successfully";
    }
}