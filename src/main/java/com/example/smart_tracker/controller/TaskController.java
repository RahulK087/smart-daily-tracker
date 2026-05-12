package com.example.smart_tracker.controller;

import com.example.smart_tracker.entity.Task;
import com.example.smart_tracker.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/")
    public String home() {

        return "index";
    }

    @GetMapping("/tasks")
    public String viewTasks(Model model) {

        model.addAttribute("tasks", taskService.getAllTasks());
        model.addAttribute("task", new Task());

        return "tasks";
    }

    @PostMapping("/saveTask")
    public String saveTask(@ModelAttribute Task task) {

        taskService.saveTask(task);

        return "redirect:/tasks";
    }

    @GetMapping("/deleteTask/{id}")
    public String deleteTask(@PathVariable Long id) {

        taskService.deleteTask(id);

        return "redirect:/tasks";
    }

    @GetMapping("/editTask/{id}")
    public String editTask(@PathVariable Long id,
                           Model model) {

        Task task = taskService.getTaskById(id);

        model.addAttribute("task", task);
        model.addAttribute("tasks",
                taskService.getAllTasks());

        return "tasks";
    }

    @GetMapping("/completeTask/{id}")
    public String completeTask(@PathVariable Long id) {

        Task task = taskService.getTaskById(id);

        if (task != null) {

            task.setCompleted(true);

            taskService.saveTask(task);
        }

        return "redirect:/tasks";
    }
}