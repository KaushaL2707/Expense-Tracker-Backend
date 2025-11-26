package com.expense.backend.service;

import com.expense.backend.model.Budget;
import com.expense.backend.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    public Optional<Budget> getBudget(Long userId, String month) {
        return budgetRepository.findByUserIdAndMonth(userId, month);
    }

    public Budget setBudget(Budget budget) {
        Optional<Budget> existing = budgetRepository.findByUserIdAndMonth(budget.getUserId(), budget.getMonth());
        if (existing.isPresent()) {
            Budget b = existing.get();
            b.setAmount(budget.getAmount());
            return budgetRepository.save(b);
        }
        return budgetRepository.save(budget);
    }
}
