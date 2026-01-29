package com.rightspend.rightspend_backend.service;

import com.rightspend.rightspend_backend.dto.DashboardResponse;
import com.rightspend.rightspend_backend.dto.ExpenseRequest;
import com.rightspend.rightspend_backend.dto.ExpenseResponse;
import com.rightspend.rightspend_backend.exception.ResourceNotFoundException;
import com.rightspend.rightspend_backend.model.Expense;
import com.rightspend.rightspend_backend.model.User;
import com.rightspend.rightspend_backend.repository.ExpenseRepository;
import com.rightspend.rightspend_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public ExpenseService(
            ExpenseRepository expenseRepository,
            UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    /* ================= ADD EXPENSE ================= */

    public void addExpense(ExpenseRequest request, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Expense expense = new Expense();
        expense.setTitle(request.getTitle());
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setDate(request.getDate());
        expense.setUser(user);

        expenseRepository.save(expense);
    }

    /* ================= GET EXPENSES ================= */

    public List<ExpenseResponse> getExpenses(String email) {

        return expenseRepository.findByUserEmailOrderByDateDesc(email)
                .stream()
                .map(e -> new ExpenseResponse(
                        e.getId(),
                        e.getTitle(),
                        e.getAmount(),
                        e.getCategory(),
                        e.getDate()))
                .toList();
    }

    /* ================= UPDATE EXPENSE ================= */

    public void updateExpense(Long id, ExpenseRequest request, String email) {

        Expense expense = expenseRepository
                .findByIdAndUserEmail(id, email)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found"));

        expense.setTitle(request.getTitle());
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setDate(request.getDate());

        expenseRepository.save(expense);
    }

    /* ================= DELETE EXPENSE ================= */

    public void deleteExpense(Long id, String email) {

        Expense expense = expenseRepository
                .findByIdAndUserEmail(id, email)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found"));

        expenseRepository.delete(expense);
    }

    /* ================= SUMMARY ================= */

    public double getTotalExpense(String email) {
        return expenseRepository.getTotalExpense(email);
    }

    public List<Map<String, Object>> getCategorySummary(String email) {

        return expenseRepository.getCategorySummary(email)
                .stream()
                .map(row -> Map.of(
                        "category", row[0],
                        "total", row[1]))
                .toList();
    }

    public List<Map<String, Object>> getMonthlySummary(String email) {

        return expenseRepository.getMonthlySummary(email)
                .stream()
                .map(row -> Map.of(
                        "month", row[0] + "-" + row[1],
                        "total", row[2]))
                .toList();
    }

    /* ================= DASHBOARD ================= */

    public DashboardResponse getDashboardData(String email) {

        return new DashboardResponse(
                getTotalExpense(email),
                getCategorySummary(email),
                getMonthlySummary(email));
    }

    /* ================= DATE RANGE FILTER ================= */

    public List<ExpenseResponse> getExpensesByDateRange(
            String email,
            LocalDate startDate,
            LocalDate endDate) {

        return expenseRepository.findByDateRange(email, startDate, endDate)
                .stream()
                .map(e -> new ExpenseResponse(
                        e.getId(),
                        e.getTitle(),
                        e.getAmount(),
                        e.getCategory(),
                        e.getDate()))
                .toList();
    }
}
