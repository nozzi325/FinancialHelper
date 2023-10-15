package com.andrew.FinancialHelper.service;

import com.andrew.FinancialHelper.db.entity.Account;
import com.andrew.FinancialHelper.db.repository.AccountRepository;
import com.andrew.FinancialHelper.exception.InsufficientFundsException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransferServiceTest {
    @InjectMocks TransferService subj;
    @Mock AccountService accountService;
    @Mock TransactionService transactionService;
    @Mock CategoryService categoryService;
    @Mock AccountRepository accountRepository;

    @Test
    @DisplayName("Successful money transfer")
    void shouldTransferMoney(){
        Account accountSender = new Account();
        accountSender.setId(1L);
        accountSender.setBalance(new BigDecimal(1000));

        Account accountReceiver = new Account();
        accountReceiver.setId(2L);
        accountReceiver.setBalance(new BigDecimal(100));

        when(accountService.findAccountById(accountSender.getId())).thenReturn(accountSender);
        when(accountService.findAccountById(accountReceiver.getId())).thenReturn(accountReceiver);

        subj.transferMoney(accountSender.getId(),accountReceiver.getId(),new BigDecimal(500));

        verify(accountRepository).changeAmount(accountSender.getId(),new BigDecimal(500));
        verify(accountRepository).changeAmount(accountReceiver.getId(),new BigDecimal(600));
    }

    @Test
    @DisplayName("Should throw \"EntityNotFoundException\" exception when senderId is incorrect")
    void shouldThrowAccountNotFoundExceptionWhenSenderIdIsIncorrect(){
        when(accountService.findAccountById(1L)).thenThrow(new EntityNotFoundException("Account not found"));

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> subj.transferMoney(1L,2L, new BigDecimal(500)));
    }

    @Test
    @DisplayName("Should throw \"EntityNotFoundException\" exception when receiverId is incorrect")
    void shouldThrowAccountNotFoundExceptionWhenReceiverIdIsIncorrect(){
        when(accountService.findAccountById(1L)).thenReturn(new Account());
        when(accountService.findAccountById(2L)).thenThrow(new EntityNotFoundException("Account not found"));

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> subj.transferMoney(1L,2L, new BigDecimal(500)));
    }

    @Test
    @DisplayName("Should throw \"InsufficientFundsException\" exception " +
            "when there is not enough money at sender account.")
    void shouldThrowInsufficientFundsExceptionWhenNotEnoughMoneyOnSenderAccount(){
        Account accountSender = new Account();
        accountSender.setId(1L);
        accountSender.setBalance(new BigDecimal(20));

        Account accountReceiver = new Account();
        accountReceiver.setId(2L);
        accountReceiver.setBalance(new BigDecimal(100));

        when(accountService.findAccountById(accountSender.getId())).thenReturn(accountSender);
        when(accountService.findAccountById(accountReceiver.getId())).thenReturn(accountReceiver);

        Assertions.assertThrows(InsufficientFundsException.class,
                () -> subj.transferMoney(accountSender.getId(),accountReceiver.getId(),new BigDecimal(500)));
    }

}