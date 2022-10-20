package com.andrew.FinancialHelper.db.repository;

import com.andrew.FinancialHelper.db.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
