package com.rightspend.rightspend_backend.dto;

import java.time.LocalDate;

public class ExpenseResponse {

    private Long id;
    private String title;
    private double amount;
    private String category;
    private LocalDate date;

    public ExpenseResponse(Long id, String title, double amount, String category, LocalDate date) {
        this.id = id;
        this.title = title;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

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
}
