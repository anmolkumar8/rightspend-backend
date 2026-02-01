package com.rightspend.rightspend_backend.controller;

import com.rightspend.rightspend_backend.dto.ExpenseRequest;
import com.rightspend.rightspend_backend.dto.ExpenseResponse;
import com.rightspend.rightspend_backend.service.ExpenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    // ================= ADD EXPENSE =================
    @PostMapping
    public ResponseEntity<String> addExpense(
            @RequestBody ExpenseRequest request,
            Authentication authentication) {

        String email = authentication.getName();
        expenseService.addExpense(request, email);

        return ResponseEntity.ok("Expense added successfully");
    }

    // ================= GET EXPENSES =================
    @GetMapping
    public ResponseEntity<List<ExpenseResponse>> getExpenses(
            Authentication authentication) {

        String email = authentication.getName();
        return ResponseEntity.ok(expenseService.getExpenses(email));
    }

    // ================= UPDATE EXPENSE =================
    @PutMapping("/{id}")
    public ResponseEntity<String> updateExpense(
            @PathVariable Long id,
            @RequestBody ExpenseRequest request,
            Authentication authentication) {

        String email = authentication.getName();
        expenseService.updateExpense(id, request, email);

        return ResponseEntity.ok("Expense updated successfully");
    }

    // ================= DELETE EXPENSE =================
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpense(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();
        expenseService.deleteExpense(id, email);

        return ResponseEntity.ok("Expense deleted successfully");
    }

    // ================= SUMMARY APIs =================

    // TOTAL EXPENSE
    @GetMapping("/summary/total")
    public ResponseEntity<Double> getTotalExpense(Authentication authentication) {

        String email = authentication.getName();
        return ResponseEntity.ok(
                expenseService.getTotalExpense(email));
    }

    // CATEGORY SUMMARY
    @GetMapping("/summary/category")
    public ResponseEntity<List<Map<String, Object>>> getCategorySummary(
            Authentication authentication) {

        String email = authentication.getName();
        return ResponseEntity.ok(
                expenseService.getCategorySummary(email));
    }

    // MONTHLY SUMMARY
    @GetMapping("/summary/monthly")
    public ResponseEntity<List<Map<String, Object>>> getMonthlySummary(
            Authentication authentication) {

        String email = authentication.getName();
        return ResponseEntity.ok(
                expenseService.getMonthlySummary(email));
    }

    // DASHBOARD API
    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboard(Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                expenseService.getDashboardData(email));
    }

}
