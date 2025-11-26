package com.expense.backend.controller;

import com.expense.backend.model.Transaction;
import com.expense.backend.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "*") // allow Flutter app to call API
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // 1️⃣ GET all transactions for a user
    @GetMapping("/{userId}")
    public List<Transaction> getUserTransactions(@PathVariable Long userId) {
        return transactionService.getTransactionsByUserId(userId);
    }

    // 2️⃣ ADD transaction
    @PostMapping
    public Transaction addTransaction(@RequestBody Transaction transaction) {
        return transactionService.addTransaction(transaction);
    }

    // 3️⃣ UPDATE transaction
    @PutMapping("/{id}")
    public Transaction updateTransaction(
            @PathVariable Long id,
            @RequestBody Transaction updated) {
        return transactionService.updateTransaction(id, updated);
    }

    // 4️⃣ DELETE transaction
    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
    }
}
