package com.andrew.FinancialHelper.db.repository;

import com.andrew.FinancialHelper.db.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findTransactionsByLocalDateBetween(LocalDate start, LocalDate end);
}
