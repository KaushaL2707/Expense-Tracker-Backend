package com.expense.backend.repository;

import com.expense.backend.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserId(Long userId);

    List<Transaction> findByUserIdAndDateBetween(
            Long userId,
            LocalDate start,
            LocalDate end
    );
}
