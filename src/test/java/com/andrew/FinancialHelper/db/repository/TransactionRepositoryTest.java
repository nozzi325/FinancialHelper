package com.andrew.FinancialHelper.db.repository;

import com.andrew.FinancialHelper.db.entity.Transaction;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest(properties = {
        "spring.test.database.replace=none",
        "spring.datasource.url=jdbc:tc:postgresql:15-alpine:///transactions"
})
@ActiveProfiles("test")
@Sql(scripts={"classpath:data/TransactionRepositoryTest.sql"})
class TransactionRepositoryTest {
    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    void shouldFindTransactionsByLocalDatePeriods() {
        // Given
        LocalDate start = LocalDate.parse("2022-10-11");
        LocalDate end = LocalDate.parse("2022-10-13");

        // When
        List<Transaction> transactions = transactionRepository.findTransactionsByLocalDateBetween(start, end);

        // Then
        assertEquals(2, transactions.size());
    }

    @Test
    void shouldFindTransactionsByCategory() {
        // Given
        Long categoryId = 2L;

        // When
        List<Transaction> transactions = transactionRepository.findTransactionsByCategory_Id(categoryId);

        // Then
        Long actualCategoryId = transactions.get(0).getCategory().getId();
        assertEquals(1, transactions.size());
        assertEquals(categoryId, actualCategoryId);
    }

    @Test
    void shouldFindTransactionsByAccountId() {
        // Given
        Long accountId = 2L;

        // When
        List<Transaction> transactions = transactionRepository.findTransactionsByAccountId(accountId);

        // Then
        Long actualAccountId = transactions.get(0).getAccount().getId();
        assertEquals(1, transactions.size());
        assertEquals(accountId, actualAccountId);
    }
}