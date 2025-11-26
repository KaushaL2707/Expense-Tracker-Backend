package com.expense.backend.controller;

import com.expense.backend.model.Transaction;
import com.expense.backend.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class ReportController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/{userId}/export")
    public ResponseEntity<byte[]> exportTransactions(
            @PathVariable Long userId,
            @RequestParam(required = false) String month) {

        YearMonth currentMonth;
        if (month != null && !month.isEmpty()) {
            currentMonth = YearMonth.parse(month);
        } else {
            currentMonth = YearMonth.now();
        }

        LocalDate start = currentMonth.atDay(1);
        LocalDate end = currentMonth.atEndOfMonth();

        List<Transaction> transactions = transactionService.getTransactionsByUserIdAndDateBetween(userId, start, end);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(baos);

        // CSV Header
        writer.println("Date,Title,Category,Type,Amount,Notes");

        for (Transaction t : transactions) {
            writer.printf("%s,%s,%s,%s,%.2f,%s%n",
                    t.getDate(),
                    escapeSpecialCharacters(t.getTitle()),
                    escapeSpecialCharacters(t.getCategory()),
                    t.getType(),
                    t.getAmount(),
                    escapeSpecialCharacters(t.getNotes()));
        }

        writer.flush();
        writer.close();

        byte[] csvBytes = baos.toByteArray();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=transactions_" + month + ".csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(csvBytes);
    }

    private String escapeSpecialCharacters(String data) {
        if (data == null) return "";
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }
}
