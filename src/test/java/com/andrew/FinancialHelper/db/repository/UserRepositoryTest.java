package com.andrew.FinancialHelper.db.repository;

import com.andrew.FinancialHelper.db.entity.User;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {
    @Autowired UserRepository subj;

    @AfterEach
    void tearDown() {
        subj.deleteAll();
    }

    @Test
    void shouldReturnUserWhenUserEmailExists() {
        //given
        String email = "DummyUser1@dummy.com";
        User dummyUser = new User();
        dummyUser.setEmail(email);
        dummyUser.setPassword("password");
        subj.save(dummyUser);

        //when
        var user = subj.findUserByEmail(email);

        assertTrue(user.isPresent());
    }

    @Test
    void shouldNotReturnUserWhenUserEmailDoesNotExist() {
        //given
        String email = "DummyUser1@dummy.com";

        //when
        var user = subj.findUserByEmail(email);

        assertFalse(user.isPresent());
    }
}