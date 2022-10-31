package com.andrew.FinancialHelper.service;

import com.andrew.FinancialHelper.db.entity.Transaction;
import com.andrew.FinancialHelper.db.repository.TransactionRepository;
import com.andrew.FinancialHelper.exception.TransactionNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {
    @InjectMocks TransactionService subj;
    @Mock TransactionRepository transactionRepository;

    @Test
    void shouldGetTransactionById() {
        Long id = 1L;
        Transaction transaction = new Transaction();
        when(transactionRepository.findById(id)).thenReturn(Optional.of(transaction));

        subj.getTransaction(id);

        verify(transactionRepository).findById(id);
        assertSame(transaction,transactionRepository.findById(id).get());
    }

    @Test
    void shouldThrowTransactionNotFoundExceptionWhenTransactionIdDoesNotExist() {
        Long id = 1L;
        when(transactionRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(TransactionNotFoundException.class,() -> subj.getTransaction(id));
    }

    @Test
    void shouldGetAllTransactions() {
        subj.getTransactions();

        verify(transactionRepository).findAll();
    }

    @Test
    void shouldCreateTransaction() {
        Transaction transaction = new Transaction();
        transaction.setLocalDate(LocalDate.parse("2022-10-15"));

        subj.createTransaction(transaction);

        ArgumentCaptor<Transaction> transactionArgumentCaptor =
                ArgumentCaptor.forClass(Transaction.class);
        verify(transactionRepository).save(transactionArgumentCaptor.capture());
        Transaction capturedTransaction = transactionArgumentCaptor.getValue();

        assertEquals(transaction, capturedTransaction);
    }

    @Test
    void shouldCreateTransactionWithCurrentDateWhenDateIsNull() {
        Transaction transaction = new Transaction();

        subj.createTransaction(transaction);

        ArgumentCaptor<Transaction> transactionArgumentCaptor =
                ArgumentCaptor.forClass(Transaction.class);
        verify(transactionRepository).save(transactionArgumentCaptor.capture());
        Transaction capturedTransaction = transactionArgumentCaptor.getValue();

        assertEquals(LocalDate.now(), capturedTransaction.getLocalDate());
    }


    @Test
    void shouldDeleteTransaction() {
        Long id = 1L;
        when(transactionRepository.existsById(id)).thenReturn(true);

        subj.deleteTransaction(id);

        verify(transactionRepository).deleteById(id);
    }

    @Test
    void shouldThrowTransactionNotFoundExceptionWhenDeleteTransactionNotFound() {
        Long id = 1L;
        when(transactionRepository.existsById(id)).thenReturn(false);

        Assertions.assertThrows(TransactionNotFoundException.class,() -> subj.deleteTransaction(id));
        verify(transactionRepository, never()).deleteById(any());
    }


    @Test
    void shouldGetTransactionsByPeriod() {
        LocalDate start = LocalDate.parse("2022-10-15");
        LocalDate end = LocalDate.parse("2022-10-25");

        subj.getTransactionsByPeriod(start,end);

        verify(transactionRepository).findTransactionsByLocalDateBetween(start,end);
    }

    @Test
    void shouldGetTransactionsByCategoryId() {
        Long categoryId = 1L;

        subj.getTransactionsByCategoryId(categoryId);

        verify(transactionRepository).findTransactionsByCategory_Id(categoryId);
    }

    @Test
    void shouldGetTransactionsByAccountId() {
        Long accountId = 1L;

        subj.getTransactionsByAccountId(accountId);

        verify(transactionRepository).findTransactionsByAccountId(accountId);
    }
}