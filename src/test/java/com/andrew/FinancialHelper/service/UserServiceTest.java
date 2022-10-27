package com.andrew.FinancialHelper.service;

import com.andrew.FinancialHelper.db.entity.User;
import com.andrew.FinancialHelper.db.repository.UserRepository;
import com.andrew.FinancialHelper.exception.UserEmailAlreadyTakenException;
import com.andrew.FinancialHelper.exception.UserNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks UserService subj;
    @Mock UserRepository userRepository;
    @Mock DigestService digestService;

    @Test
    void shouldGetUserById() {
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.of(new User()));

        subj.findUserById(id);

        verify(userRepository).findById(id);
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenUserIdDoesNotExist() {
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () ->  subj.findUserById(id));
    }

    @Test
    void shouldGetAllUsers() {
        subj.findAll();

        verify(userRepository).findAll();
    }

    @Test
    void shouldCreateUser() {
        User user = new User();
        user.setEmail("email");
        user.setPassword("password");
        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(digestService.hash(user.getPassword())).thenReturn("hashPassword");

        subj.createUser(user);

        ArgumentCaptor<User> userArgumentCaptor =
                ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User captureUser = userArgumentCaptor.getValue();
        assertEquals("hashPassword",captureUser.getPassword());
    }

    @Test
    void shouldThrowEmailAlreadyTakenExceptionWhenCreatingUserWithExistingEmail() {
        User user = new User();
        user.setEmail("email");
        when(userRepository.findUserByEmail(user.getEmail()))
                .thenThrow(new UserEmailAlreadyTakenException("User with this email already exists"));

        assertThrows(UserEmailAlreadyTakenException.class,() -> subj.createUser(user));
        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldDeleteUser() {
        Long id = 1L;
        when(userRepository.existsById(id)).thenReturn(true);

        subj.deleteUser(id);

        verify(userRepository).deleteById(id);
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenDeleteUserNotFound(){
        Long id = 1L;
        when(userRepository.existsById(id)).thenReturn(false);

        assertThrows(UserNotFoundException.class,()-> subj.deleteUser(id));
        verify(userRepository, never()).deleteById(any());
    }

    @Test
    void shouldGetUserByEmail() {
        String email = "email";
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(new User()));

        subj.findUser(email);

        verify(userRepository).findUserByEmail(email);
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenUserEmailNotFound() {
        String email = "email";
        when(userRepository.findUserByEmail(email))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> subj.findUser(email));
    }
}