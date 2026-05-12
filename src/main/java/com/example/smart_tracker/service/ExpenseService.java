package com.example.smart_tracker.service;

import com.example.smart_tracker.entity.Expense;
import com.example.smart_tracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public void saveExpense(Expense expense) {

        if (expense.getExpenseDate() == null) {

            expense.setExpenseDate(LocalDate.now());
        }

        double foodLimit = 3000;

        double entertainmentLimit = 5000;

        double rentLimit = 10000;

        double transportLimit = 2000;

        double othersLimit = 4000;

        double categoryTotal =
                expenseRepository.findAll()
                        .stream()
                        .filter(e ->
                                e.getCategory()
                                        .equalsIgnoreCase(
                                                expense.getCategory()))
                        .mapToDouble(Expense::getAmount)
                        .sum();

        double newTotal =
                categoryTotal + expense.getAmount();

        String category =
                expense.getCategory();

        if(category.equalsIgnoreCase("Food")
                && newTotal > foodLimit){

            expense.setWarning(true);
        }

        if(category.equalsIgnoreCase("Entertainment")
                && newTotal > entertainmentLimit){

            expense.setWarning(true);
        }

        if(category.equalsIgnoreCase("Rent")
                && newTotal > rentLimit){

            expense.setWarning(true);
        }

        if(category.equalsIgnoreCase("Transport")
                && newTotal > transportLimit){

            expense.setWarning(true);
        }

        if(category.equalsIgnoreCase("Others")
                && newTotal > othersLimit){

            expense.setWarning(true);
        }

        expenseRepository.save(expense);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    public double getCategoryTotal(String category){

        return expenseRepository.findAll()
                .stream()
                .filter(expense ->
                        expense.getCategory()
                                .equalsIgnoreCase(category))
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    public double getTotalExpense() {

        return expenseRepository.findAll()
                .stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }
}