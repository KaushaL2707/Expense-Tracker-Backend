package com.expense.backend.controller;

import com.expense.backend.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
@CrossOrigin(origins = "*")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/{userId}")
    public Map<String, Object> getMonthlyAnalytics(
            @PathVariable Long userId,
            @RequestParam(required = false) String month) {
        return analyticsService.getMonthlyAnalytics(userId, month);
    }
}
