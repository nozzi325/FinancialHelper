package com.andrew.FinancialHelper.service;

import com.andrew.FinancialHelper.db.entity.Account;
import com.andrew.FinancialHelper.db.entity.Category;
import com.andrew.FinancialHelper.db.entity.Transaction;
import com.andrew.FinancialHelper.db.repository.AccountRepository;
import com.andrew.FinancialHelper.exception.InsufficientFundsException;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class TransferService {
    private final AccountService accountService;
    private final TransactionService transactionService;
    private final CategoryService categoryService;
    private final AccountRepository accountRepository;
    @Transactional
    public void transferMoney(Long senderId, Long receiverId, BigDecimal amount){
        Account sender = accountService.findAccountById(senderId);
        Account receiver = accountService.findAccountById(receiverId);

        if (sender.getBalance().compareTo(amount) < 0){
            throw new InsufficientFundsException("Insufficient funds on the account : " + senderId);
        }
        BigDecimal senderNewAmount =
                sender.getBalance().subtract(amount);
        BigDecimal receiverNewAmount =
                receiver.getBalance().add(amount);

        accountRepository
                .changeAmount(senderId, senderNewAmount);
        accountRepository
                .changeAmount(receiverId, receiverNewAmount);

        Category transferCategory = categoryService.findCategoryById(1L);

        Transaction transactionFrom = new Transaction();
        transactionFrom.setAccount(sender);
        transactionFrom.setResult(amount.multiply(BigDecimal.valueOf(-1)));
        transactionFrom.setCategory(transferCategory);

        Transaction transactionTo = new Transaction();
        transactionTo.setAccount(receiver);
        transactionTo.setResult(amount);
        transactionTo.setCategory(transferCategory);

        transactionService.createTransaction(transactionFrom);
        transactionService.createTransaction(transactionTo);
    }
}
