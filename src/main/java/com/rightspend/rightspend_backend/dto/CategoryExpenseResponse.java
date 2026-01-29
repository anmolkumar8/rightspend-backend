package com.rightspend.rightspend_backend.dto;

public class CategoryExpenseResponse {

    private String category;
    private double totalAmount;

    public CategoryExpenseResponse(String category, double totalAmount) {
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
