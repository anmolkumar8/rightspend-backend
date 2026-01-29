package com.rightspend.rightspend_backend.dto;

public class ExpenseCategorySummary {

    private String category;
    private double totalAmount;

    public ExpenseCategorySummary(String category, double totalAmount) {
        this.category = category;
        this.totalAmount = totalAmount;
    }

    public String getCategory() {
        return category;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}
