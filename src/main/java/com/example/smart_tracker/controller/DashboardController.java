package com.example.smart_tracker.controller;

import com.example.smart_tracker.entity.User;
import com.example.smart_tracker.service.ExpenseService;
import com.example.smart_tracker.service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/dashboard")
    public String dashboard(Model model,
                            HttpSession session) {

        model.addAttribute("totalTasks",
                taskService.getAllTasks().size());

        model.addAttribute("completedTasks",
                taskService.getAllTasks()
                        .stream()
                        .filter(task -> task.isCompleted())
                        .count());

        model.addAttribute("pendingTasks",
                taskService.getAllTasks()
                        .stream()
                        .filter(task -> !task.isCompleted())
                        .count());

        model.addAttribute("totalExpense",
                expenseService.getTotalExpense());

        model.addAttribute("foodExpense",
                expenseService.getCategoryTotal("Food"));

        model.addAttribute("transportExpense",
                expenseService.getCategoryTotal("Transport"));

        model.addAttribute("entertainmentExpense",
                expenseService.getCategoryTotal("Entertainment"));

        model.addAttribute("rentExpense",
                expenseService.getCategoryTotal("Rent"));

        return "dashboard";
    }
}