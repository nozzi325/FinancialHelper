package com.andrew.FinancialHelper.db.repository;

import com.andrew.FinancialHelper.db.entity.Account;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql(scripts={"classpath:data/AccountRepositoryTest.sql"})
class AccountRepositoryTest {
    @Autowired AccountRepository subj;

    @Test
    void shouldUpdateAccountBalance() {
        //given
        BigDecimal expected = new BigDecimal(50.00).setScale(2);

        //when
        subj.changeAmount(1L,expected);

        //then
        Account updatedAccount = subj.findById(1L).get();
        BigDecimal actual = updatedAccount.getBalance().setScale(2);
        assertEquals(expected,actual);
    }
}