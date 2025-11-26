package com.expense.backend.controller;

import com.expense.backend.model.Budget;
import com.expense.backend.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.Optional;

@RestController
@RequestMapping("/api/budgets")
@CrossOrigin(origins = "*")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @GetMapping("/{userId}")
    public Budget getBudget(
            @PathVariable Long userId,
            @RequestParam(required = false) String month) {
        
        String targetMonth = (month != null && !month.isEmpty()) ? month : YearMonth.now().toString();
        
        return budgetService.getBudget(userId, targetMonth)
                .orElse(null);
    }

    @PostMapping
    public Budget setBudget(@RequestBody Budget budget) {
        return budgetService.setBudget(budget);
    }
}
