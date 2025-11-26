package com.expense.backend.service;

import com.expense.backend.model.Budget;
import com.expense.backend.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AnalyticsService {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private BudgetService budgetService;

    public Map<String, Object> getMonthlyAnalytics(Long userId, String month) {
        YearMonth currentMonth;
        if (month != null && !month.isEmpty()) {
            currentMonth = YearMonth.parse(month);
        } else {
            currentMonth = YearMonth.now();
        }
        
        LocalDate start = currentMonth.atDay(1);
        LocalDate end = currentMonth.atEndOfMonth();

        List<Transaction> transactions = transactionService.getTransactionsByUserIdAndDateBetween(userId, start, end);

        double totalIncome = 0;
        double totalExpense = 0;
        Map<String, Double> categoryBreakdown = new HashMap<>();
        Map<String, Double> dailyBreakdown = new HashMap<>();

        for (Transaction t : transactions) {
            String day = t.getDate().toString();
            if ("income".equalsIgnoreCase(t.getType())) {
                totalIncome += t.getAmount();
            } else if ("expense".equalsIgnoreCase(t.getType())) {
                totalExpense += t.getAmount();
                categoryBreakdown.merge(t.getCategory(), t.getAmount(), Double::sum);
                dailyBreakdown.merge(day, t.getAmount(), Double::sum);
            }
        }

        double balance = totalIncome - totalExpense;

        Optional<Budget> budgetOpt = budgetService.getBudget(userId, currentMonth.toString());
        double budgetLimit = budgetOpt.map(Budget::getAmount).orElse(0.0);

        Map<String, Object> response = new HashMap<>();
        response.put("totalIncome", totalIncome);
        response.put("totalExpense", totalExpense);
        response.put("balance", balance);
        response.put("budgetLimit", budgetLimit);
        response.put("categoryBreakdown", categoryBreakdown);
        response.put("dailyBreakdown", dailyBreakdown);
        
        // Calculate budget percentage
        if (budgetLimit > 0) {
            response.put("budgetPercentage", (totalExpense / budgetLimit) * 100);
        } else {
            response.put("budgetPercentage", 0);
        }

        return response;
    }
}
