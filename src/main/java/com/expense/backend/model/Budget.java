package com.expense.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "budgets")
@Data
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Double amount;
    
    private String month; // Format: "YYYY-MM"
}
