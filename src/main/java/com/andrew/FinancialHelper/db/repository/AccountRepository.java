package com.andrew.FinancialHelper.db.repository;

import com.andrew.FinancialHelper.db.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Modifying
    @Query("UPDATE Account SET balance = :amount WHERE id = :id")
    void changeAmount(Long id, BigDecimal amount);
}
