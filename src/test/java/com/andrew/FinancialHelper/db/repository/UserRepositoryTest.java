package com.andrew.FinancialHelper.db.repository;

import com.andrew.FinancialHelper.db.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest(properties = {
        "spring.test.database.replace=none",
        "spring.datasource.url=jdbc:tc:postgresql:15-alpine:///general"
})
@ActiveProfiles("test")
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldFindUserByEmail() {
        // Given
        User user = new User();
        user.setEmail("testuser@test.com");
        user.setPassword("password");
        userRepository.save(user);

        // When
        Optional<User> foundUser = userRepository.findUserByEmail("testuser@test.com");

        // Then
        assertTrue(foundUser.isPresent());
        assertEquals(user.getEmail(), foundUser.get().getEmail());
    }

    @Test
    void shouldNotFindUserByEmail() {
        // When
        Optional<User> foundUser = userRepository.findUserByEmail("nonexistent@test.com");

        // Then
        assertTrue(foundUser.isEmpty());
    }
}