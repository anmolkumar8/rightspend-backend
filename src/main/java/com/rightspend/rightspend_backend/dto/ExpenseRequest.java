package com.rightspend.rightspend_backend.dto;

import java.time.LocalDate;

public class ExpenseRequest {

    private String title;
    private double amount;
    private String category;
    private LocalDate date;

    // ✅ REQUIRED GETTERS
    public String getTitle() {
        return title;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getDate() {
        return date;
    }

    // ✅ REQUIRED SETTERS
    public void setTitle(String title) {
        this.title = title;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
