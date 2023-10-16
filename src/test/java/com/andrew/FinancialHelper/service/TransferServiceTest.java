package com.andrew.FinancialHelper.service;

import com.andrew.FinancialHelper.db.entity.Account;
import com.andrew.FinancialHelper.db.entity.Category;
import com.andrew.FinancialHelper.db.entity.Transaction;
import com.andrew.FinancialHelper.db.repository.AccountRepository;
import com.andrew.FinancialHelper.dto.request.TransactionRequest;
import com.andrew.FinancialHelper.exception.InsufficientFundsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TransferServiceTest {

    @InjectMocks
    private TransferService transferService;

    @Mock
    private AccountService accountService;

    @Mock
    private TransactionService transactionService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private AccountRepository accountRepository;

    private static final Long TRANSFER_CATEGORY_ID = 1L;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenSufficientFunds_whenTransferMoney_thenAccountsAndTransactionsAreUpdated() {
        Account sender = new Account();
        sender.setId(1L);
        sender.setBalance(new BigDecimal("100.00"));

        Account receiver = new Account();
        receiver.setId(2L);
        receiver.setBalance(new BigDecimal("50.00"));

        TransactionRequest transactionRequestFrom = new TransactionRequest();
        transactionRequestFrom.setAccountId(sender.getId());
        transactionRequestFrom.setCategoryId(TRANSFER_CATEGORY_ID);
        transactionRequestFrom.setResult(new BigDecimal("-30.00"));

        TransactionRequest transactionRequestTo = new TransactionRequest();
        transactionRequestTo.setAccountId(receiver.getId());
        transactionRequestTo.setCategoryId(TRANSFER_CATEGORY_ID);
        transactionRequestTo.setResult(new BigDecimal("30.00"));

        Mockito.when(accountService.findAccountById(sender.getId())).thenReturn(sender);
        Mockito.when(accountService.findAccountById(receiver.getId())).thenReturn(receiver);
        Mockito.when(categoryService.findCategoryById(TRANSFER_CATEGORY_ID)).thenReturn(new Category());
        Mockito.when(transactionService.createTransaction(transactionRequestFrom)).thenReturn(new Transaction());
        Mockito.when(transactionService.createTransaction(transactionRequestTo)).thenReturn(new Transaction());

        transferService.transferMoney(sender.getId(), receiver.getId(), new BigDecimal("30.00"));

        assertEquals(new BigDecimal("70.00"), sender.getBalance());
        assertEquals(new BigDecimal("80.00"), receiver.getBalance());
    }

    @Test
    void givenInsufficientFunds_whenTransferMoney_thenInsufficientFundsExceptionIsThrown() {
        Account sender = new Account();
        sender.setId(1L);
        sender.setBalance(new BigDecimal("100.00"));

        Account receiver = new Account();
        receiver.setId(2L);
        receiver.setBalance(new BigDecimal("50.00"));

        Mockito.when(accountService.findAccountById(sender.getId())).thenReturn(sender);
        Mockito.when(accountService.findAccountById(receiver.getId())).thenReturn(receiver);

        // Attempting to transfer more funds than the sender has.
        BigDecimal transferAmount = new BigDecimal("150.00");

        assertThrows(InsufficientFundsException.class,
                () -> transferService.transferMoney(sender.getId(), receiver.getId(), transferAmount));

        // Make sure balances are not changed.
        assertEquals(new BigDecimal("100.00"), sender.getBalance());
        assertEquals(new BigDecimal("50.00"), receiver.getBalance());
    }

    @Test
    void givenNonExistentSenderAccount_whenTransferMoney_thenEntityNotFoundExceptionIsThrownForSenderAccount() {
        // Sender account does not exist.
        Mockito.when(accountService.findAccountById(1L)).thenThrow(new EntityNotFoundException("Account not found"));

        // Receiver account is valid.
        Account receiver = new Account();
        receiver.setId(2L);
        receiver.setBalance(new BigDecimal("50.00"));
        Mockito.when(accountService.findAccountById(receiver.getId())).thenReturn(receiver);

        BigDecimal transferAmount = new BigDecimal("30.00");

        // Verify that EntityNotFoundException is thrown for the sender account.
        assertThrows(EntityNotFoundException.class,
                () -> transferService.transferMoney(1L, receiver.getId(), transferAmount));

        // Verify that balances are not changed.
        assertEquals(new BigDecimal("50.00"), receiver.getBalance());
    }

    @Test
    void givenNonExistentReceiverAccount_whenTransferMoney_thenEntityNotFoundExceptionIsThrownForReceiverAccount() {
        // Sender account is valid.
        Account sender = new Account();
        sender.setId(1L);
        sender.setBalance(new BigDecimal("100.00"));
        Mockito.when(accountService.findAccountById(sender.getId())).thenReturn(sender);

        // Receiver account does not exist.
        Mockito.when(accountService.findAccountById(2L)).thenThrow(new EntityNotFoundException("Account not found"));

        BigDecimal transferAmount = new BigDecimal("30.00");

        // Verify that EntityNotFoundException is thrown for the receiver account.
        assertThrows(EntityNotFoundException.class,
                () -> transferService.transferMoney(sender.getId(), 2L, transferAmount));

        // Verify that balances are not changed.
        assertEquals(new BigDecimal("100.00"), sender.getBalance());
    }

}