package com.andrew.FinancialHelper.service;

import com.andrew.FinancialHelper.db.entity.Account;
import com.andrew.FinancialHelper.db.repository.AccountRepository;
import com.andrew.FinancialHelper.exception.AccountNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    @InjectMocks AccountService subj;
    @Mock AccountRepository accountRepository;

    @Test
    void shouldGetAllAccounts() {
        subj.findAllAccounts();

        verify(accountRepository).findAll();
    }

    @Test
    void shouldGetAccountById() {
        Account account = new Account();
        Long id = 1L;
        when(accountRepository.findById(id)).thenReturn(Optional.of(account));

        subj.findAccountById(id);

        verify(accountRepository).findById(id);
        assertEquals(account,accountRepository.findById(id).get());
    }

    @Test
    void shouldThrowAccountNotFoundExceptionWhenAccountDoesNotExist() {
        Long id = 1L;
        when(accountRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class,() -> subj.findAccountById(id));
    }

    @Test
    void shouldCreateAccount() {
        Account account = new Account();

        subj.createAccount(account);

        ArgumentCaptor<Account> accountArgumentCaptor =
                ArgumentCaptor.forClass(Account.class);
        verify(accountRepository).save(accountArgumentCaptor.capture());
        Account capturedAccount = accountArgumentCaptor.getValue();
        assertEquals(account, capturedAccount);
    }

    @Test
    void shouldDeleteAccount() {
        Long id = 1L;
        when(accountRepository.existsById(id)).thenReturn(true);

        subj.deleteAccount(id);

        verify(accountRepository).deleteById(id);
    }

    @Test
    void shouldThrowAccountNotFoundExceptionWhenDeleteAccountNotFound() {
        Long id = 1L;
        when(accountRepository.existsById(id)).thenReturn(false);

        Assertions.assertThrows(AccountNotFoundException.class,() -> subj.deleteAccount(id));
        verify(accountRepository, never()).deleteById(any());
    }


    @Test
    void shouldUpdateAccount() {
        Long id = 1L;
        Account account = new Account();
        account.setId(id);
        when(accountRepository.existsById(id)).thenReturn(true);

        subj.updateAccount(account);

        ArgumentCaptor<Account> accountArgumentCaptor =
                ArgumentCaptor.forClass(Account.class);
        verify(accountRepository).save(accountArgumentCaptor.capture());
        Account capturedAccount = accountArgumentCaptor.getValue();
        assertEquals(account, capturedAccount);
    }

    @Test
    void shouldThrowAccountNotFoundExceptionWhenUpdateAccountNotFound() {
        Long id = 1L;
        Account account = new Account();
        account.setId(id);
        when(accountRepository.existsById(id)).thenReturn(false);

        assertThrows(AccountNotFoundException.class,() -> subj.updateAccount(account));
        verify(accountRepository, never()).save(any());
    }
}