package com.rightspend.rightspend_backend.dto;

import java.util.List;
import java.util.Map;

public class DashboardResponse {

    private Double totalExpense;
    private List<Map<String, Object>> categorySummary;
    private List<Map<String, Object>> monthlySummary;

    public DashboardResponse(
            Double totalExpense,
            List<Map<String, Object>> categorySummary,
            List<Map<String, Object>> monthlySummary) {
        this.totalExpense = totalExpense;
        this.categorySummary = categorySummary;
        this.monthlySummary = monthlySummary;
    }

    public Double getTotalExpense() {
        return totalExpense;
    }

    public List<Map<String, Object>> getCategorySummary() {
        return categorySummary;
    }

    public List<Map<String, Object>> getMonthlySummary() {
        return monthlySummary;
    }
}
