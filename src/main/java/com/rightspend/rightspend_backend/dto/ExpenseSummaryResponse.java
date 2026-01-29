package com.rightspend.rightspend_backend.dto;

public class ExpenseSummaryResponse {

    private double totalAmount;

    public ExpenseSummaryResponse(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}
