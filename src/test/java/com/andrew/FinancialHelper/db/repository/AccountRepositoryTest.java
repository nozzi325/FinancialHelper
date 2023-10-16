package com.andrew.FinancialHelper.db.repository;

import com.andrew.FinancialHelper.db.entity.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest(properties = {
        "spring.test.database.replace=none",
        "spring.datasource.url=jdbc:tc:postgresql:15-alpine:///accounts"
})
@ActiveProfiles("test")
@Sql(scripts = "classpath:data/AccountRepositoryTest.sql")
class AccountRepositoryTest {
    @Autowired
    private AccountRepository accountRepository;

    @Test
    void shouldUpdateAccountBalance() {
        // Given
        Long accountId = 1L;
        BigDecimal newBalance = new BigDecimal("50.00");

        // When
        accountRepository.changeAmount(accountId, newBalance);

        // Then
        Account updatedAccount = accountRepository.findById(accountId).orElse(null);
        assertEquals(newBalance, updatedAccount.getBalance());
    }
}