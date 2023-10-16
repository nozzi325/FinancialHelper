package com.andrew.FinancialHelper.service;

import com.andrew.FinancialHelper.db.entity.User;
import com.andrew.FinancialHelper.db.repository.UserRepository;
import com.andrew.FinancialHelper.exception.UserEmailAlreadyTakenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private DigestService digestService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenValidUserId_whenFindingUserById_thenUserIsFound() {
        // Given
        long userId = 1L;
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // When
        User foundUser = userService.findUserById(userId);

        // Then
        assertEquals(user, foundUser);
    }

    @Test
    void givenInvalidUserId_whenFindingUserById_thenEntityNotFoundExceptionThrown() {
        // Given
        long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When and Then
        assertThrows(EntityNotFoundException.class, () -> userService.findUserById(userId));
    }

    @Test
    void givenValidUserRequest_whenCreatingUser_thenUserIsCreated() {
        // Given
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");

        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(digestService.hash(user.getPassword())).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // When
        userService.createUser(user);

        // Then
        verify(userRepository).save(user);
    }

    @Test
    void givenUserWithEmailAlreadyTaken_whenCreatingUser_thenUserEmailAlreadyTakenExceptionThrown() {
        // Given
        User user = new User();
        user.setEmail("test@example.com");

        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(Optional.of(user));

        // When and Then
        assertThrows(UserEmailAlreadyTakenException.class, () -> userService.createUser(user));
    }

    @Test
    void givenValidUserId_whenDeletingUser_thenUserIsDeleted() {
        // Given
        long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(true);

        // When
        userService.deleteUser(userId);

        // Then
        verify(userRepository).deleteById(userId);
    }

    @Test
    void givenInvalidUserId_whenDeletingUser_thenEntityNotFoundExceptionThrown() {
        // Given
        long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(false);

        // When and Then
        assertThrows(EntityNotFoundException.class, () -> userService.deleteUser(userId));
    }

    @Test
    void givenValidUserRequest_whenUpdatingUser_thenUserIsUpdated() {
        // Given
        User user = new User();
        user.setId(1L);
        user.setEmail("new@example.com");
        user.setPassword("newPassword");

        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setEmail("old@example.com");
        existingUser.setPassword("oldPassword");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(digestService.hash(user.getPassword())).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        // When
        userService.updateUser(user);

        // Then
        assertEquals("new@example.com", existingUser.getEmail());
        assertEquals("hashedPassword", existingUser.getPassword());
    }

    @Test
    void givenSameEmailInUserRequest_whenUpdatingUser_thenUserIsUpdated() {
        // Given
        User user = new User();
        user.setId(1L);
        user.setEmail("same@example.com");
        user.setPassword("newPassword");

        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setEmail("same@example.com");
        existingUser.setPassword("oldPassword");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(digestService.hash(user.getPassword())).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        // When
        userService.updateUser(user);

        // Then
        assertEquals("same@example.com", existingUser.getEmail());
        assertEquals("hashedPassword", existingUser.getPassword());
    }

    @Test
    void givenUserNotFound_whenUpdatingUser_thenEntityNotFoundExceptionThrown() {
        // Given
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // When and Then
        assertThrows(EntityNotFoundException.class, () -> userService.updateUser(user));
    }

    @Test
    void givenValidEmail_whenFindingUserByEmail_thenUserIsFound() {
        // Given
        String email = "test@example.com";
        User user = new User();
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(user));

        // When
        User foundUser = userService.findUserByEmail(email);

        // Then
        assertEquals(user, foundUser);
    }

    @Test
    void givenInvalidEmail_whenFindingUserByEmail_thenEntityNotFoundExceptionThrown() {
        // Given
        String email = "nonexistent@example.com";
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.empty());

        // When and Then
        assertThrows(EntityNotFoundException.class, () -> userService.findUserByEmail(email));
    }
}