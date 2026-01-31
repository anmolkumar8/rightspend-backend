package com.rightspend.rightspend_backend.repository;

import com.rightspend.rightspend_backend.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    /* ================= BASIC QUERIES ================= */

    List<Expense> findByUserEmailOrderByDateDesc(String email);

    Optional<Expense> findByIdAndUserEmail(Long id, String email);

    /* ================= TOTAL EXPENSE ================= */

    @Query("""
                SELECT COALESCE(SUM(e.amount), 0)
                FROM Expense e
                WHERE e.user.email = :email
            """)
    Double getTotalExpense(@Param("email") String email);

    /* ================= CATEGORY SUMMARY ================= */

    @Query("""
                SELECT e.category, COALESCE(SUM(e.amount), 0)
                FROM Expense e
                WHERE e.user.email = :email
                GROUP BY e.category
            """)
    List<Object[]> getCategorySummary(@Param("email") String email);

    /* ================= MONTHLY SUMMARY (POSTGRES FIX) ================= */

    @Query(value = """
                SELECT
                    EXTRACT(YEAR FROM e.date) AS year,
                    EXTRACT(MONTH FROM e.date) AS month,
                    COALESCE(SUM(e.amount), 0)
                FROM expenses e
                JOIN users u ON e.user_id = u.id
                WHERE u.email = :email
                GROUP BY year, month
                ORDER BY year, month
            """, nativeQuery = true)
    List<Object[]> getMonthlySummary(@Param("email") String email);

    /* ================= DATE RANGE FILTER ================= */

    @Query("""
                SELECT e FROM Expense e
                WHERE e.user.email = :email
                AND e.date BETWEEN :startDate AND :endDate
                ORDER BY e.date DESC
            """)
    List<Expense> findByDateRange(
            @Param("email") String email,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}
