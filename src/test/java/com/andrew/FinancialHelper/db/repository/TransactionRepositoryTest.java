package com.andrew.FinancialHelper.db.repository;

import com.andrew.FinancialHelper.db.entity.Transaction;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql(scripts={"classpath:data/TransactionRepositoryTest.sql"})
class TransactionRepositoryTest {
    @Autowired TransactionRepository subj;

    @Test
    void shouldFindTransactionsByLocalDatePeriods() {
        //given
        LocalDate start = LocalDate.parse("2022-10-11");
        LocalDate end = LocalDate.parse("2022-10-13");
        //when
        List<Transaction> transactions = subj.findTransactionsByLocalDateBetween(start, end);
        //then
        assertEquals(2,transactions.size());
    }

    @Test
    void shouldFindTransactionsByCategory() {
        //given
        Long categoryId = 2L;
        //when
        List<Transaction> transactions = subj.findTransactionsByCategory_Id(categoryId);
        //then
        Long actualCategoryId = transactions.get(0).getCategory().getId();
        assertEquals(1,transactions.size());
        assertEquals(categoryId,actualCategoryId);
    }

    @Test
    void shouldFindTransactionsByAccountId() {
        //given
        Long accountId = 2L;
        //when
        List<Transaction> transactions = subj.findTransactionsByAccountId(accountId);
        //then
        Long actualAccountId = transactions.get(0).getAccount().getId();
        assertEquals(1,transactions.size());
        assertEquals(accountId,actualAccountId);
    }
}