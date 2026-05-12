package com.example.smart_tracker.controller;

import com.example.smart_tracker.entity.Expense;
import com.example.smart_tracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    private double monthlyBudget = 20000;

    @GetMapping("/expenses")
    public String viewExpenses(Model model) {

        double totalExpense =
                expenseService.getTotalExpense();

        double remainingBudget =
                monthlyBudget - totalExpense;

        double budgetPercentage =
                (totalExpense / monthlyBudget) * 100;

        model.addAttribute("expense",
                new Expense());

        model.addAttribute("expenses",
                expenseService.getAllExpenses());

        model.addAttribute("totalExpense",
                totalExpense);

        model.addAttribute("monthlyBudget",
                monthlyBudget);

        model.addAttribute("remainingBudget",
                remainingBudget);

        model.addAttribute("budgetPercentage",
                budgetPercentage);

        double foodUsed =
                expenseService.getCategoryTotal("Food");

        double transportUsed =
                expenseService.getCategoryTotal("Transport");

        double entertainmentUsed =
                expenseService.getCategoryTotal("Entertainment");

        model.addAttribute("foodLimit", 3000);
        model.addAttribute("foodUsed", foodUsed);
        model.addAttribute("foodRemaining",
                3000 - foodUsed);

        model.addAttribute("transportLimit", 2000);
        model.addAttribute("transportUsed", transportUsed);
        model.addAttribute("transportRemaining",
                2000 - transportUsed);

        model.addAttribute("entertainmentLimit", 5000);
        model.addAttribute("entertainmentUsed",
                entertainmentUsed);

        model.addAttribute("entertainmentRemaining",
                5000 - entertainmentUsed);

        return "expenses";
    }

    @PostMapping("/saveExpense")
    public String saveExpense(@ModelAttribute Expense expense) {

        expenseService.saveExpense(expense);

        return "redirect:/expenses";
    }

    @PostMapping("/updateBudget")
    public String updateBudget(@RequestParam double budget){

        monthlyBudget = budget;

        return "redirect:/expenses";
    }

    @GetMapping("/deleteExpense/{id}")
    public String deleteExpense(@PathVariable Long id) {

        expenseService.deleteExpense(id);

        return "redirect:/expenses";
    }
}