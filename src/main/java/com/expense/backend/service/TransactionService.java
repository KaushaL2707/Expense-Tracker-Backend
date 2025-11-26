package com.expense.backend.service;

import com.expense.backend.model.Transaction;
import com.expense.backend.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> getTransactionsByUserId(Long userId) {
        return transactionRepository.findByUserId(userId);
    }

    public Transaction addTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Transaction updateTransaction(Long id, Transaction updatedTransaction) {
        return transactionRepository.findById(id)
                .map(transaction -> {
                    transaction.setTitle(updatedTransaction.getTitle());
                    transaction.setAmount(updatedTransaction.getAmount());
                    transaction.setCategory(updatedTransaction.getCategory());
                    transaction.setDate(updatedTransaction.getDate());
                    transaction.setNotes(updatedTransaction.getNotes());
                    transaction.setType(updatedTransaction.getType());
                    return transactionRepository.save(transaction);
                })
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
    
    public List<Transaction> getTransactionsByUserIdAndDateBetween(Long userId, LocalDate start, LocalDate end) {
        return transactionRepository.findByUserIdAndDateBetween(userId, start, end);
    }
}
