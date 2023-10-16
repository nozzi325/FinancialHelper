package com.andrew.FinancialHelper.service;

import com.andrew.FinancialHelper.db.entity.Account;
import com.andrew.FinancialHelper.db.entity.Category;
import com.andrew.FinancialHelper.db.entity.Transaction;
import com.andrew.FinancialHelper.db.repository.TransactionRepository;
import com.andrew.FinancialHelper.dto.request.TransactionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountService accountService;

    @Mock
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenValidTransactionId_whenFindingTransactionById_thenTransactionIsFound() {
        // Given
        long transactionId = 1L;
        Transaction transaction = new Transaction();
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction));

        // When
        Transaction foundTransaction = transactionService.getTransactionById(transactionId);

        // Then
        assertNotNull(foundTransaction);
        assertEquals(transaction, foundTransaction);
    }

    @Test
    void givenInvalidTransactionId_whenFindingTransactionById_thenEntityNotFoundExceptionThrown() {
        // Given
        long transactionId = 1L;
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.empty());

        // When and Then
        assertThrows(EntityNotFoundException.class, () -> transactionService.getTransactionById(transactionId));
    }

    @Test
    void givenValidTransactionRequest_whenCreatingTransaction_thenTransactionIsCreated() {
        // Given
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAccountId(1L);
        transactionRequest.setCategoryId(1L);
        transactionRequest.setResult(new BigDecimal("1000.00"));

        Account account = new Account();
        when(accountService.findAccountById(1L)).thenReturn(account);

        Category category = new Category();
        when(categoryService.findCategoryById(1L)).thenReturn(category);

        Transaction savedTransaction = new Transaction();
        when(transactionRepository.save(any(Transaction.class))).thenReturn(savedTransaction);

        // When
        Transaction createdTransaction = transactionService.createTransaction(transactionRequest);

        // Then
        assertNotNull(createdTransaction);
        assertEquals(savedTransaction, createdTransaction);
    }

    @Test
    void givenInvalidTransactionRequest_whenCreatingTransaction_thenEntityNotFoundExceptionThrown() {
        // Given
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAccountId(1L);
        transactionRequest.setCategoryId(1L);
        transactionRequest.setResult(new BigDecimal("1000.00"));

        when(accountService.findAccountById(1L)).thenReturn(new Account());
        when(categoryService.findCategoryById(1L)).thenThrow(new EntityNotFoundException());

        // When and Then
        assertThrows(EntityNotFoundException.class, () -> transactionService.createTransaction(transactionRequest));
    }

    @Test
    void givenValidTransactionId_whenDeletingTransaction_thenTransactionIsDeleted() {
        // Given
        long transactionId = 1L;
        when(transactionRepository.existsById(transactionId)).thenReturn(true);

        // When
        transactionService.deleteTransaction(transactionId);

        // Then
        verify(transactionRepository, times(1)).deleteById(transactionId);
    }

    @Test
    void givenInvalidTransactionId_whenDeletingTransaction_thenEntityNotFoundExceptionThrown() {
        // Given
        long transactionId = 1L;
        when(transactionRepository.existsById(transactionId)).thenReturn(false);

        // When and Then
        assertThrows(EntityNotFoundException.class, () -> transactionService.deleteTransaction(transactionId));
    }
}