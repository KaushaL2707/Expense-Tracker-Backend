package com.expense.backend.repository;

import com.expense.backend.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    Optional<Budget> findByUserIdAndMonth(Long userId, String month);
}
