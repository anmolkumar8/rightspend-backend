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

    /* ================= MONTHLY SUMMARY ================= */

    @Query("""
                SELECT
                    FUNCTION('YEAR', e.date),
                    FUNCTION('MONTH', e.date),
                    COALESCE(SUM(e.amount), 0)
                FROM Expense e
                WHERE e.user.email = :email
                GROUP BY FUNCTION('YEAR', e.date), FUNCTION('MONTH', e.date)
                ORDER BY FUNCTION('YEAR', e.date), FUNCTION('MONTH', e.date)
            """)
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
