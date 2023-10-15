package com.andrew.FinancialHelper.service;

import com.andrew.FinancialHelper.db.entity.Account;
import com.andrew.FinancialHelper.db.entity.User;
import com.andrew.FinancialHelper.db.repository.AccountRepository;
import com.andrew.FinancialHelper.dto.request.AccountRequest;
import com.andrew.FinancialHelper.exception.UserAccountLimitExceededException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenValidAccountId_whenFindingAccountById_thenAccountIsFound() {
        // Given
        long accountId = 1L;
        Account account = new Account();
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        // When
        Account foundAccount = accountService.findAccountById(accountId);

        // Then
        assertNotNull(foundAccount);
        assertEquals(account, foundAccount);
    }

    @Test
    void givenInvalidAccountId_whenFindingAccountById_thenEntityNotFoundExceptionThrown() {
        // Given
        long accountId = 1L;
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        // When and Then
        assertThrows(EntityNotFoundException.class, () -> accountService.findAccountById(accountId));
    }

    @Test
    void givenValidAccountRequest_whenCreatingAccount_thenAccountIsCreated() {
        // Given
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setOwnerId(1L);
        accountRequest.setName("Test Account");
        accountRequest.setBalance(new BigDecimal("1000.00"));

        User user = new User();
        user.setAccounts(Collections.emptyList());
        when(userService.findUserById(1L)).thenReturn(user);

        Account savedAccount = new Account();
        when(accountRepository.save(any(Account.class))).thenReturn(savedAccount);

        // When
        Account createdAccount = accountService.createAccountFromRequest(accountRequest);

        // Then
        assertNotNull(createdAccount);
        assertEquals(savedAccount, createdAccount);
    }

    @Test
    void givenUserHasReachedAccountLimit_whenCreatingAccount_thenUserAccountLimitExceededExceptionThrown() {
        // Given
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setOwnerId(1L);
        accountRequest.setName("Test Account");
        accountRequest.setBalance(new BigDecimal("1000.00"));

        User user = new User();
        when(userService.findUserById(1L)).thenReturn(user);

        int maxAccounts = (int) ReflectionTestUtils.getField(AccountService.class, "MAX_ACCOUNTS_PER_USER");
        List<Account> userAccounts = new ArrayList<>();
        for (int i = 0; i < maxAccounts; i++) {
            userAccounts.add(new Account());
        }
        user.setAccounts(userAccounts);

        // When and Then
        assertThrows(UserAccountLimitExceededException.class, () -> accountService.createAccountFromRequest(accountRequest));
    }

    @Test
    void givenValidAccountRequest_whenUpdatingAccount_thenAccountIsUpdated() {
        // Given
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setId(1L);
        accountRequest.setName("Updated Account");
        accountRequest.setBalance(new BigDecimal("2000.00"));

        Account existingAccount = new Account();
        when(accountRepository.findById(1L)).thenReturn(Optional.of(existingAccount));
        when(accountRepository.save(any(Account.class))).thenReturn(existingAccount);

        // When
        accountService.updateAccountFromRequest(accountRequest);

        // Then
        assertEquals("Updated Account", existingAccount.getName());
        assertEquals(new BigDecimal("2000.00"), existingAccount.getBalance());
    }

    @Test
    void givenInvalidAccountRequest_whenUpdatingAccount_thenEntityNotFoundExceptionThrown() {
        // Given
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setId(1L);

        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        // When and Then
        assertThrows(EntityNotFoundException.class, () -> accountService.updateAccountFromRequest(accountRequest));
    }

    @Test
    void givenValidAccountId_whenDeletingAccount_thenAccountIsDeleted() {
        // Given
        long accountId = 1L;
        when(accountRepository.existsById(accountId)).thenReturn(true);

        // When
        accountService.deleteAccountById(accountId);

        // Then
        verify(accountRepository, times(1)).deleteById(accountId);
    }

    @Test
    void givenInvalidAccountId_whenDeletingAccount_thenEntityNotFoundExceptionThrown() {
        // Given
        long accountId = 1L;
        when(accountRepository.existsById(accountId)).thenReturn(false);

        // When and Then
        assertThrows(EntityNotFoundException.class, () -> accountService.deleteAccountById(accountId));
    }
}